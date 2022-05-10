package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums.GoogleDirectionsStatus;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.GoogleResponseStatusHandler;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google.GoogleDirectionsResponseStatusHandler;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import com.jileklu2.bakalarska_prace_app.parsers.GoogleJsonParser;
import com.jileklu2.bakalarska_prace_app.builders.scriptBuilders.HttpRequestStringBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class GoogleRouteStepFinder {
    private static final GoogleResponseStatusHandler responseStatusHandler = new GoogleDirectionsResponseStatusHandler();

    private static int totalStepsNum = 0;

    public static void findRouteSteps(Route route, Boolean optimize)
    throws IdenticalCoordinatesException, DistanceOutOfBoundsException, DurationOutOfBoundsException,
    CoordinatesOutOfBoundsException, RouteLengthExceededException, RequestDeniedException, OverDailyLimitException,
    OverQueryLimitException, WaypointsNumberExceededException, ZeroResultsException, InvalidRequestException,
    DataNotAvailableException, LocationNotFoundException, UnknownStatusException {
        if(route == null || optimize == null)
            throw new NullPointerException("Arguments can't be null.");

        if(!route.getRouteSteps().isEmpty())
            route.getRouteSteps().clear();

        List<Route> helpingRoutes = RouteSplitter.splitRoute(route);

        for(Route helpingRoute : helpingRoutes) {
            findRouteStepsInfo(helpingRoute, optimize);
            totalStepsNum++;
        }

        totalStepsNum = 0;

        for(int i = 0; i < helpingRoutes.size(); i++) {
            if(i == 0)
                route.setOrigin(helpingRoutes.get(i).getOrigin());

            route.addRouteSteps(helpingRoutes.get(i).getRouteSteps());

            if(i + 1 != helpingRoutes.size()) {
                Route helpingRoute = new Route(helpingRoutes.get(i).getDestination(),
                                               helpingRoutes.get(i+1).getOrigin()
                );
                int tmpStepNumber = helpingRoutes.get(i+1).getRouteSteps().get(0).getStepNumber() - 1;

                findRouteStepsInfo(helpingRoute, optimize);
                helpingRoute.getRouteSteps().get(0).setStepNumber(tmpStepNumber);
                route.addRouteStep(helpingRoute.getRouteSteps().get(0));
            }

            if(i == helpingRoutes.size() - 1)
                route.setDestination(helpingRoutes.get(i).getDestination());
        }


    }

    private static void findRouteStepsInfo(Route route, Boolean optimize)
    throws DistanceOutOfBoundsException, DurationOutOfBoundsException, CoordinatesOutOfBoundsException,
    RouteLengthExceededException, RequestDeniedException, OverDailyLimitException, OverQueryLimitException,
    WaypointsNumberExceededException, ZeroResultsException, InvalidRequestException, DataNotAvailableException,
    LocationNotFoundException, UnknownStatusException {
        JSONObject response = getRouteResponse(route, optimize);
        String status = response.getString("status");

        if(!route.getRouteSteps().isEmpty())
            route.getRouteSteps().clear();

        if(!status.equalsIgnoreCase(GoogleDirectionsStatus.OK.name())){
            responseStatusHandler.handle(status);
        } else {
            LinkedHashSet<Coordinates> newWaypointsOrder = new LinkedHashSet<>();
            List<RouteStep> newRouteSteps = new ArrayList<>();
            JSONArray routes = response.getJSONArray("routes");

            for(int i = 0; i < routes.length(); i++) {
                JSONObject jsonRoute = routes.getJSONObject(i);
                JSONArray legs = jsonRoute.getJSONArray("legs");

                for(int j = 0; j < legs.length(); j++) {
                    JSONObject jsonLeg = legs.getJSONObject(j);

                    Coordinates stepOrigin;

                    if( i == 0 && j == 0)
                        route.setOrigin(GoogleJsonParser.parseOrigin(jsonLeg));

                    stepOrigin = GoogleJsonParser.parseOrigin(jsonLeg);

                    Coordinates stepDestination;
                    if(i == routes.length() - 1 && j == legs.length() - 1)
                        route.setDestination(GoogleJsonParser.parseDestination(jsonLeg));

                    stepDestination = GoogleJsonParser.parseDestination(jsonLeg);

                    int stepDistance = GoogleJsonParser.parseDistance(jsonLeg);
                    int stepDuration = GoogleJsonParser.parseDuration(jsonLeg);

                    newRouteSteps.add(
                        new RouteStep(stepOrigin, stepDestination, (double) stepDistance,
                            (double) stepDuration, totalStepsNum)
                    );

                    totalStepsNum++;
                }

                if (optimize) {
                    newWaypointsOrder.addAll(
                        GetOptimizedWaypoints(newRouteSteps)
                    );
                }
            }
            route.setRouteSteps(newRouteSteps);

            if(optimize) {
                route.setWaypoints(newWaypointsOrder);
            }
        }
    }

    private static JSONObject getRouteResponse(Route route, boolean optimize) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(HttpRequestStringBuilder.googleDirectionsRequest(route, optimize)))
            .build();

        String response = String.valueOf(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .join());

        return new JSONObject(response);
    }

    private static LinkedHashSet<Coordinates> GetOptimizedWaypoints(List<RouteStep> routeSteps) {
        LinkedHashSet<Coordinates> orderedWaypoints = new LinkedHashSet<>();
        for(int i = 1; i < routeSteps.size(); i++) {
            orderedWaypoints.add(new Coordinates(routeSteps.get(i).getOrigin()));
        }

        return orderedWaypoints;
    }
}

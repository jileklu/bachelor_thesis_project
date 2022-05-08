package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.handlers.errorHandlers.ErrorHandler;
import com.jileklu2.bakalarska_prace_app.handlers.errorHandlers.GoogleRoutingErrorHandler;
import com.jileklu2.bakalarska_prace_app.errors.GoogleDirectionsStatus;
import com.jileklu2.bakalarska_prace_app.errors.GoogleRoutingError;
import com.jileklu2.bakalarska_prace_app.errors.RoutingError;
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
    public static void findRouteSteps(Route route, Boolean optimize) {
        JSONObject response = getRouteResponse(route, optimize);
        String status = response.getString("status");

        if(!status.equalsIgnoreCase(GoogleDirectionsStatus.OK.name())){
            GoogleDirectionsStatus errorStatus = GoogleDirectionsStatus.valueOf(status);
            RoutingError error = new GoogleRoutingError(errorStatus);
            ErrorHandler errorHandler = new GoogleRoutingErrorHandler((GoogleRoutingError) error);
            errorHandler.handleError();
        } else {
            LinkedHashSet<Coordinates> newWaypointsOrder = new LinkedHashSet<>();
            List<RouteStep> newRouteSteps = new ArrayList<>();
            JSONArray routes = response.getJSONArray("routes");

            int totalStepsNum = 0;

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

    public static void findRouteStepDetails(RouteStep routeStep, boolean optimize) {

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

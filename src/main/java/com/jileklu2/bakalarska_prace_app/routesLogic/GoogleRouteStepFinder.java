package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.errorHandlers.ErrorHandler;
import com.jileklu2.bakalarska_prace_app.errorHandlers.GoogleRoutingErrorHandler;
import com.jileklu2.bakalarska_prace_app.errors.GoogleDirectionsStatus;
import com.jileklu2.bakalarska_prace_app.errors.GoogleRoutingError;
import com.jileklu2.bakalarska_prace_app.errors.RoutingError;
import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.mapObjects.RouteStep;
import com.jileklu2.bakalarska_prace_app.parsers.GoogleJsonStepParser;
import com.jileklu2.bakalarska_prace_app.scriptBuilders.HttpRequestStringBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class GoogleRouteStepFinder {
    public static void findRouteSteps(Route route, Boolean optimize) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpRequestStringBuilder.googleDirectionsRequest(route, optimize)))
                .build();

        String response = String.valueOf(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join());

        JSONObject jsonResponse = new JSONObject(response);

        String status = jsonResponse.getString("status");

        if(!status.equalsIgnoreCase(GoogleDirectionsStatus.OK.name())){
            GoogleDirectionsStatus errorStatus = GoogleDirectionsStatus.valueOf(status);
            RoutingError error = new GoogleRoutingError(errorStatus);
            ErrorHandler errorHandler = new GoogleRoutingErrorHandler((GoogleRoutingError) error);
            errorHandler.handleError();
        } else {
            LinkedHashSet<Coordinates> newWaypointsOrder = new LinkedHashSet<>();
            List<RouteStep> routeSteps = new ArrayList<>();
            JSONArray routes = jsonResponse.getJSONArray("routes");

            int totalStepsNum = 0;

            for(int i = 0; i < routes.length(); i++) {
                JSONObject jsonRoute = routes.getJSONObject(i);
                JSONArray legs = jsonRoute.getJSONArray("legs");

                for(int j = 0; j < legs.length(); j++) {
                    JSONObject jsonLeg = legs.getJSONObject(j);
                    JSONArray steps = jsonLeg.getJSONArray("steps");

                    for(int k = 0; k < steps.length(); k++) {
                        JSONObject jsonStep = steps.getJSONObject(k);
                        Coordinates stepOrigin = GoogleJsonStepParser.parseOrigin(jsonStep);
                        Coordinates stepDestination = GoogleJsonStepParser.parseDestination(jsonStep);
                        int stepDistance = GoogleJsonStepParser.parseDistance(jsonStep);
                        int stepDuration = GoogleJsonStepParser.parseDuration(jsonStep);

                        routeSteps.add(
                                new RouteStep(stepOrigin, stepDestination, (double) stepDistance,
                                        (double) stepDuration, totalStepsNum)
                        );

                        totalStepsNum++;
                    }
                }

                if (optimize) {
                    newWaypointsOrder.addAll(
                            GetOptimizedWaypoints(jsonRoute, new ArrayList<>(route.getWaypoints()))
                    );
                }
            }

            route.setRouteSteps(routeSteps);

            if(optimize) {
                route.setWaypoints(newWaypointsOrder);
            }
        }
    }

    private static LinkedHashSet<Coordinates> GetOptimizedWaypoints(JSONObject jsonRoute,
                                                                    ArrayList<Coordinates> oldWaypoints) {
        LinkedHashSet<Coordinates> orderedWaypoints = new LinkedHashSet<>();
        JSONArray waypointsOrder = jsonRoute.getJSONArray("waypoint_order");
        for(int i = 0; i < waypointsOrder.length(); i++) {
            int index = waypointsOrder.getInt(i);
            orderedWaypoints.add(oldWaypoints.get(index));
        }

        return orderedWaypoints;
    }
}

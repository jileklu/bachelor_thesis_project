package com.jileklu2.bakalarska_prace_app.routeInfoFinders;

import com.jileklu2.bakalarska_prace_app.builders.scriptBuilders.HttpRequestStringBuilder;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.here.HereResponseStatusHandler;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums.HereOptimizationStatus;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.here.HereOptimizationResponseStatusHandler;
import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;

public class HereWaypointOptimization {
    private static final HereResponseStatusHandler responseStatusHandler = new HereOptimizationResponseStatusHandler();

    /**
     *
     * @param route
     * @throws CoordinatesOutOfBoundsException
     * @throws CreatedException
     * @throws RequestDeniedException
     * @throws OverDailyLimitException
     * @throws LocationNotFoundException
     * @throws UnknownStatusException
     */
    public static void optimizeWaypoints(Route route) throws CoordinatesOutOfBoundsException, CreatedException,
    RequestDeniedException, OverDailyLimitException, LocationNotFoundException, UnknownStatusException {
        if(route == null)
            throw new NullPointerException("Arguments can't be null.");

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(HttpRequestStringBuilder.hereWaypointsOptimizationRequest(route)))
            .build();

        JSONObject jsonResponse = getOptimizationResponse(request);

        int status = jsonResponse.getInt("responseCode");

        if(status != HereOptimizationStatus.OK.value){
            responseStatusHandler.handle(String.valueOf(status));
        } else {
            JSONArray results = jsonResponse.getJSONArray("results");
            JSONArray waypointsJson = results.getJSONObject(0).getJSONArray("waypoints");

            HashSet<Coordinates> newWaypoints = new HashSet<>();

            for(int i = 1; i < waypointsJson.length() - 1; i++) {
                JSONObject waypoint = waypointsJson.getJSONObject(i);
                newWaypoints.add(new Coordinates(waypoint.getDouble("lat"), waypoint.getDouble("lng")));
            }

            route.setWaypoints(newWaypoints);
        }
    }

    /**
     *
     * @param request
     * @return
     */
    private static JSONObject getOptimizationResponse(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();

        String response = String.valueOf(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .join());

        return new JSONObject(response);
    }
}

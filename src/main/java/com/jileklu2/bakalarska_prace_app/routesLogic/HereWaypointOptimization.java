package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.builders.scriptBuilders.HttpRequestStringBuilder;
import com.jileklu2.bakalarska_prace_app.errors.HereResponseStatus;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;

public class HereWaypointOptimization {
    public static void optimizeWaypoints(Route route) throws CoordinatesOutOfBoundsException {
        if(route == null)
            throw new NullPointerException("Arguments can't be null.");

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(HttpRequestStringBuilder.hereWaypointsOptimizationRequest(route)))
            .build();

        JSONObject jsonResponse = getOptimizationResponse(request);

        int status = jsonResponse.getInt("responseCode");

        if(status != HereResponseStatus.OK.value){
            //todo
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

    private static JSONObject getOptimizationResponse(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();

        String response = String.valueOf(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .join());

        return new JSONObject(response);
    }
}
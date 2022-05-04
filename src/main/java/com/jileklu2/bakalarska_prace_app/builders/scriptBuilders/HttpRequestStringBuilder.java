package com.jileklu2.bakalarska_prace_app.builders.scriptBuilders;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;

public class HttpRequestStringBuilder {
    private static final String googleApiKey = "AIzaSyBwJIjpDQrNDJG0Z-DFtb7hc9M1eaZAmP4";

    public static String googleDirectionsRequest(Route route, Boolean optimize) {
        StringBuilder requestStringBuilder = new StringBuilder();
        requestStringBuilder.append("https://maps.googleapis.com/maps/api/directions/json?");
        requestStringBuilder.append("origin=");
        requestStringBuilder.append(route.getOrigin().getLat()).append("%2C");
        requestStringBuilder.append(route.getOrigin().getLng());
        requestStringBuilder.append("&destination=");
        requestStringBuilder.append(route.getDestination().getLat()).append("%2C");
        requestStringBuilder.append(route.getDestination().getLng());

        if(route.getWaypoints().size() != 0) {
            requestStringBuilder.append("&waypoints=");
            if(optimize)
                requestStringBuilder.append("optimize" + "%3A" + "true" + "%7C");

            List<String> waypointsStrings = new ArrayList<>();
            for(Coordinates waypoint : route.getWaypoints()) {
                if(!optimize)
                    waypointsStrings.add("via%3A");

                waypointsStrings.add(waypoint.getLat() + "%2C" + waypoint.getLng());
            }
            StringJoiner joiner = new StringJoiner("%7C");
            for (String partString : waypointsStrings) {
                joiner.add(partString);
            }
            requestStringBuilder.append(joiner);
        }
        requestStringBuilder.append("&key=" + googleApiKey);

        return requestStringBuilder.toString();
    }

    public static String googleElevationRequest(Route route) {
        StringBuilder requestStringBuilder = new StringBuilder();

        requestStringBuilder.append("https://maps.googleapis.com/maps/api/elevation/json?");
        requestStringBuilder.append("locations=");
        requestStringBuilder.append(route.getOrigin().getLat()).append("%2C");
        requestStringBuilder.append(route.getOrigin().getLng());
        requestStringBuilder.append("%7C");
        requestStringBuilder.append(route.getDestination().getLat()).append("%2C");
        requestStringBuilder.append(route.getDestination().getLng());

        for(Coordinates waypoint : route.getWaypoints()) {
            requestStringBuilder.append("%7C");
            requestStringBuilder.append(waypoint.getLat()).append("%2C");
            requestStringBuilder.append(waypoint.getLng());
        }

        for(RouteStep step : route.getRouteSteps()) {
            requestStringBuilder.append("%7C");
            requestStringBuilder.append(step.getOrigin().getLat()).append("%2C");
            requestStringBuilder.append(step.getOrigin().getLng());
            requestStringBuilder.append("%7C");
            requestStringBuilder.append(step.getDestination().getLat()).append("%2C");
            requestStringBuilder.append(step.getDestination().getLng());
        }

        requestStringBuilder.append("&key=" + googleApiKey);

        return requestStringBuilder.toString();
    }

    public static String googleMatrixRequest( List<Coordinates> origins,
                                              List<Coordinates> destinations,
                                              LocalDateTime timeStamp) {
        StringBuilder requestStringBuilder = new StringBuilder();
        requestStringBuilder.append("https://maps.googleapis.com/maps/api/distancematrix/json?");
        requestStringBuilder.append("origins=");

        for(Coordinates origin : origins) {
            requestStringBuilder.append(origin.getLat()).append("%2C");
            requestStringBuilder.append(origin.getLng()).append("%7C");
        }

        requestStringBuilder.delete(requestStringBuilder.length()-3, requestStringBuilder.length()-1);
        requestStringBuilder.append("&destinations=");

        for(Coordinates destination : destinations) {
            requestStringBuilder.append(destination.getLat()).append("%2C");
            requestStringBuilder.append(destination.getLng()).append("%7C");
        }

        requestStringBuilder.delete(requestStringBuilder.length()-3, requestStringBuilder.length()-1);
        long epochSecond = timeStamp.atZone(ZoneId.of("UTC")).toEpochSecond();

        requestStringBuilder.append("&departure_time=").append(epochSecond);
        requestStringBuilder.append("&key=" + googleApiKey);

        return requestStringBuilder.toString();
    }
}

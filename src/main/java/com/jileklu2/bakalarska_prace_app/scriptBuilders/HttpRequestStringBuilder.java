package com.jileklu2.bakalarska_prace_app.scriptBuilders;

import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class HttpRequestStringBuilder {
    private static final String googleApiKey = "AIzaSyBwJIjpDQrNDJG0Z-DFtb7hc9M1eaZAmP4";
    private static final String tomtomApiKey = "juW8gxNDN04q1vB0IjuMc66QwoE1V08x";

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
}

package com.jileklu2.bakalarska_prace_app.builders.scriptBuilders;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyCoordinatesListException;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public class HttpRequestStringBuilder {
    private static final String googleApiKey = "AIzaSyBwJIjpDQrNDJG0Z-DFtb7hc9M1eaZAmP4";
    private static final String hereApiKey = "aU1GP1NBOpOFOOGnicr0IHXXA8iAoK18spAb2krayhM";

    public static String googleDirectionsRequest(Route route, Boolean optimize) {
        if(route == null || optimize == null)
            throw new NullPointerException("Arguments can't be null.");

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
        if(route == null)
            throw new NullPointerException("Arguments can't be null.");

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

    public static String googleElevationRequest(List<Coordinates> coordinatesList)
        throws EmptyCoordinatesListException {
        if(coordinatesList == null)
            throw new NullPointerException("Arguments can't be null.");

        if(coordinatesList.isEmpty())
            throw new EmptyCoordinatesListException("Request can't be created with an empty list.");

        StringBuilder requestStringBuilder = new StringBuilder();

        requestStringBuilder.append("https://maps.googleapis.com/maps/api/elevation/json?");
        requestStringBuilder.append("locations=");

        for(int i = 0; i < coordinatesList.size(); i++) {
            requestStringBuilder.append(coordinatesList.get(i).getLat()).append("%2C");
            requestStringBuilder.append(coordinatesList.get(i).getLng());

            if( i != coordinatesList.size() - 1)
                requestStringBuilder.append("%7C");
        }

        requestStringBuilder.append("&key=" + googleApiKey);

        return requestStringBuilder.toString();
    }

    public static String googleMatrixRequest( List<Coordinates> origins,
                                              List<Coordinates> destinations,
                                              LocalDateTime timeStamp)
        throws EmptyDestinationsListException {
        if(origins == null || destinations == null || timeStamp == null)
            throw new NullPointerException("Arguments can't be null.");

        if(origins.isEmpty())
            throw new EmptyDestinationsListException("Matrix request can't be created with an empty origins list.");

        if(destinations.isEmpty())
            throw new EmptyDestinationsListException("Matrix request can't be created with an empty directions list.");

        StringBuilder requestStringBuilder = new StringBuilder();
        requestStringBuilder.append("https://maps.googleapis.com/maps/api/distancematrix/json?");
        requestStringBuilder.append("origins=");

        for(Coordinates origin : origins) {
            requestStringBuilder.append(origin.getLat()).append("%2C");
            requestStringBuilder.append(origin.getLng()).append("%7C");
        }

        requestStringBuilder.delete(requestStringBuilder.length()-3, requestStringBuilder.length());
        requestStringBuilder.append("&destinations=");

        for(Coordinates destination : destinations) {
            requestStringBuilder.append(destination.getLat()).append("%2C");
            requestStringBuilder.append(destination.getLng()).append("%7C");
        }

        requestStringBuilder.delete(requestStringBuilder.length()-3, requestStringBuilder.length());
        long epochSecond = timeStamp.atZone(ZoneId.of("UTC")).toEpochSecond();

        requestStringBuilder.append("&departure_time=").append(epochSecond);
        requestStringBuilder.append("&key=" + googleApiKey);

        return requestStringBuilder.toString();
    }

    public static String hereWaypointsOptimizationRequest(Route route) {
        if(route == null)
            throw new NullPointerException("Arguments can't be null.");

        StringBuilder requestStringBuilder = new StringBuilder();
        requestStringBuilder.append("https://wse.ls.hereapi.com/2/findsequence.json?");
        requestStringBuilder.append("apiKey=");
        requestStringBuilder.append(hereApiKey);
        requestStringBuilder.append("&start=origin;");
        requestStringBuilder.append(route.getOrigin().getLat()).append(",");
        requestStringBuilder.append(route.getOrigin().getLng());

        Iterator<Coordinates> it = route.getWaypoints().iterator();
        for(int i = 0; i < route.getWaypoints().size(); i++) {
            requestStringBuilder.append("&destination").append(i+1).append("=").append(i).append(";");
            Coordinates waypoint = it.next();
            requestStringBuilder.append(waypoint.getLat()).append(",").append(waypoint.getLng());
        }


        requestStringBuilder.append("&end=destination;");
        requestStringBuilder.append(route.getDestination().getLat()).append(",");
        requestStringBuilder.append(route.getDestination().getLng());
        requestStringBuilder.append("&improveFor=distance");
        requestStringBuilder.append("&departure=now");
        requestStringBuilder.append("&mode=fastest;car;traffic:enabled;");


        return requestStringBuilder.toString();
    }
}

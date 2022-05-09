package com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects;

import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Route {
    private Coordinates origin;
    private Coordinates destination;
    private List<RouteStep> routeSteps;
    private LinkedHashSet<Coordinates> waypoints;

    @SafeVarargs
    public Route(Coordinates origin, Coordinates destination, Collection<Coordinates>... waypoints)
        throws IdenticalCoordinatesException {
        if(origin == null || destination == null)
            throw new NullPointerException("Arguments can't be null");

        checkArgumentCoordinates(origin, destination);
        this.origin = new Coordinates(origin);
        this.destination = new Coordinates(destination);
        this.routeSteps = new ArrayList<>();

        if(waypoints.length == 0 )
            this.waypoints = new LinkedHashSet<>();
        else if(waypoints[0].size() > 98)
            throw new IllegalArgumentException("Too many waypoints");
        else
            this.waypoints = new LinkedHashSet<>(waypoints[0]);
    }

    public Route(Route another) {
        if(another == null)
            throw new NullPointerException("Arguments can't be null");

        this.origin = new Coordinates(another.getOrigin());
        this.destination = new Coordinates(another.getDestination());
        this.routeSteps = new ArrayList<>(another.getRouteSteps());
        this.waypoints = new LinkedHashSet<>(another.getWaypoints());
    }

    public Route(JSONObject jsonObject) throws CoordinatesOutOfBoundsException, IdenticalCoordinatesException {
        if(jsonObject == null)
            throw new NullPointerException("Arguments can't be null");

        try{
            Coordinates origin = new Coordinates(jsonObject.getJSONObject("origin"));
            Coordinates destination = new Coordinates(jsonObject.getJSONObject("destination"));
            checkArgumentCoordinates(origin, destination);
            this.origin = origin;
            this.destination = destination;


            this.waypoints = new LinkedHashSet<>();
            JSONArray waypointsJsonArr = jsonObject.getJSONArray("waypoints");

            if(waypointsJsonArr.length() > 98)
                throw new IllegalArgumentException("Too many waypoints");

            for(int i = 0; i < waypointsJsonArr.length(); i++) {
                this.waypoints.add(new Coordinates(waypointsJsonArr.getJSONObject(i)));
            }
            this.routeSteps = new ArrayList<>();

        } catch (JSONException e) {
            throw new JSONException("Wrong JSON file structure.");
        }
    }

    private void checkArgumentCoordinates(Coordinates origin, Coordinates destination) throws IdenticalCoordinatesException {
        if(origin.equals(destination))
            throw new IdenticalCoordinatesException("Origin and destination can't be equal.");
    }

    public Coordinates getOrigin() {
        return origin;
    }

    public Coordinates getDestination() {
        return destination;
    }

    public LinkedHashSet<Coordinates> getWaypoints() {
        return waypoints;
    }

    public List<RouteStep> getRouteSteps() {
        return routeSteps;
    }

    public Double getDuration() {
        Double duration = 0.0;
        for(RouteStep routeStep : routeSteps) {
            duration += routeStep.getDuration();
        }

        return duration;
    }

    public void setOrigin(Coordinates origin) {
        if(origin == null)
            throw new NullPointerException("Arguments can't be null");

        this.origin = origin;
    }

    public void setDestination(Coordinates destination) {
        if(destination == null)
            throw new NullPointerException("Arguments can't be null");

        this.destination = destination;
    }

    public void setWaypoints(Collection<Coordinates> waypoints) {
        if(waypoints == null)
            throw new NullPointerException("Arguments can't be null");

        this.waypoints = new LinkedHashSet<>(waypoints);
    }

    public void setRouteSteps(List<RouteStep> routeSteps) {
        if(routeSteps == null)
            throw new NullPointerException("Arguments can't be null");

        this.routeSteps = new ArrayList<>(routeSteps);
    }

    public void addWaypoint(Coordinates waypoint) {
        if(waypoint == null)
            throw new NullPointerException("Arguments can't be null");

        this.waypoints.add(new Coordinates(waypoint));
    }

    public void addWaypoints(Collection<Coordinates> waypoints) {
        if(waypoints == null)
            throw new NullPointerException("Arguments can't be null");

        this.waypoints.addAll(new LinkedHashSet<>(waypoints));
    }

    public void addRouteSteps(List<RouteStep> routeSteps) {
        if(routeSteps == null)
            throw new NullPointerException("Arguments can't be null");

        this.routeSteps.addAll(routeSteps);
    }

    public void addRouteStep(RouteStep routeStep) {
        if(routeStep == null)
            throw new NullPointerException("Arguments can't be null");

        this.routeSteps.add(routeStep);
    }

    @Override
    public String toString() {
        return String.format("{origin:%s,waypoints:%s,destination:%s,duration:%s,steps:%s}",origin,waypoints,destination,
            getDuration(),routeSteps);
    }

    public JSONObject toJSON(){
        return new JSONObject(this.toString());
    }

    public String toGPX() {
        StringBuilder gpxStringBuilder = new StringBuilder("<trk>\n");
        for(RouteStep step : routeSteps) {
            gpxStringBuilder.append(step.toGPX("\t")).append("\n");
        }
        gpxStringBuilder.append("</trk>");
        return gpxStringBuilder.toString();
    }

    /**
     * Routes are equal when all of their attributes are equal
     * If route object is null then routes are always not equal
     *
     * @param obj - the reference object with which to compare.
     * @return (boolean) true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Route other = (Route) obj;

        // https://stackoverflow.com/questions/25366441/check-which-combinations-of-parameters-are-null
        int switchvar = 0;
        if (origin == null) {switchvar += 1;}
        if (destination == null) {switchvar += 10;}
        if (waypoints == null) {switchvar += 100;}

        switch (switchvar) {
            case(1):
                return other.origin == null;
            case(10):
                return other.destination == null;
            case(11):
                return other.destination == null && other.origin == null;
            case(100):
                return other.waypoints == null;
            case(101):
                return other.waypoints == null && other.origin == null;
            case(110):
                return other.waypoints == null && other.destination == null;
            case(111):
                return other.origin == null && other.destination == null && other.waypoints == null;
        }

        return this.destination.equals(other.destination) &&
                this.origin.equals(other.origin) &&
                this.waypoints.equals(other.waypoints);
    }

    @Override
    public int hashCode() {
        final int prime = 7;
        int result = 1;
        int add = 0;

        int switchvar = 0;
        if (origin == null) {switchvar += 1;}
        if (destination == null) {switchvar += 10;}
        if (waypoints == null) {switchvar += 100;}

        switch (switchvar) {
            case(0):
                add = origin.hashCode();
                break;
            case(1):
                add = destination.hashCode();
                break;
            case(11):
                add = waypoints.hashCode();
                break;
            case(111):
                add = 0;
                break;
        }

        result = prime * result + add;
        return result;
    }
}

package com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects;

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
    public Route(Coordinates origin, Coordinates destination, Collection<Coordinates>... waypoints) {
        this.origin = new Coordinates(origin);
        this.destination = new Coordinates(destination);
        this.routeSteps = new ArrayList<>();
        this.waypoints = waypoints.length > 0 ? new LinkedHashSet<>(waypoints[0]) : new LinkedHashSet<>();
    }

    public Route(Route another) {
        this.origin = new Coordinates(another.getOrigin());
        this.destination = new Coordinates(another.getDestination());
        this.routeSteps = new ArrayList<>(another.getRouteSteps());
        this.waypoints = new LinkedHashSet<>(another.getWaypoints());
    }

    public Route(JSONObject jsonObject) {
        try{
            this.origin = new Coordinates(jsonObject.getJSONObject("origin"));
            this.destination = new Coordinates(jsonObject.getJSONObject("destination"));
            this.waypoints = new LinkedHashSet<>();
            JSONArray waypointsJsonArr = jsonObject.getJSONArray("waypoints");
            for(int i = 0; i < waypointsJsonArr.length(); i++) {
                this.waypoints.add(new Coordinates(waypointsJsonArr.getJSONObject(i)));
            }
            this.routeSteps = new ArrayList<>();
        } catch (JSONException e) {
            throw new IllegalArgumentException("Wrong JSON file structure.");
        }
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

    public Coordinates getOrigin() {
        return origin;
    }

    public Coordinates getDestination() {
        return destination;
    }

    public LinkedHashSet<Coordinates> getWaypoints() {
        return waypoints;
    }

    public void setOrigin(Coordinates origin) {
        this.origin = origin;
    }

    public void setDestination(Coordinates destination) {
        this.destination = destination;
    }

    public void setWaypoints(Collection<Coordinates> waypoints) {
        this.waypoints = new LinkedHashSet<>(waypoints);
    }

    public void addWaypoint(Coordinates waypoint) {
        this.waypoints.add(new Coordinates(waypoint));
    }

    public void addWaypoints(Collection<Coordinates> waypoints) {
        this.waypoints.addAll(new LinkedHashSet<>(waypoints));
    }

    public Double getDuration() {
        Double duration = 0.0;
        for(RouteStep routeStep : routeSteps) {
            duration += routeStep.getDuration();
        }

        return duration;
    }

    public List<RouteStep> getRouteSteps() {
        return routeSteps;
    }

    public void setRouteSteps(List<RouteStep> routeSteps) {
        this.routeSteps = new ArrayList<>(routeSteps);
    }

    /**
     * Chains two routes together by following rules:
     *         this Route := A
     *         anotherRoute := B
     *         resultingRoute := C
     *      I) A.origin != (B.origin, B.destination)
     *         A.destination != (B.origin, B.destination)
     *              * C.origin = A.origin
     *              * C.destination = B.destination
     *              * C.waypoints = A.waypoints + B.waypoints + A.destination + B.origin
     *
     *      II) A.origin != (B.origin, B.destination),
     *          A.destination = B.origin, A.destination != B.destination
     *              * C.origin = A.origin
     *              * C.destination = B.destination
     *              * C.waypoints = A.waypoints + B.waypoints + A.destination
     *
     *      III) A.origin = B.destination, A.origin != B.origin,
     *           A.destination != (B.origin, B.destination)
     *              * C.origin = B.origin
     *              * C.destination = A.destination
     *              * C.waypoints = A.waypoints + B.waypoints + A.origin
     *
     *      IV) A.origin = B.origin, A.origin != B.destination,
     *          A.destination != (B.origin, B.destination)
     *              * C.origin = A.destination
     *              * C.destination = B.destination
     *              * C.waypoints = A.waypoints + B.waypoints + A.origin
     *
     *      V) A.origin != (B.origin, B.destination),
     *         A.destination = B.destination, A.destination != B.origin
     *              * C.origin = A.origin
     *              * C.destination = B.origin
     *              * C.waypoints = A.waypoints + B.waypoints + A.destination
     *
     *
     * @param anotherRoute - (Route) route to be chained with
     * @return (Route) chained route
     */
    public Route chainWithRoute(Route anotherRoute) {
        Route resultingRoute;

        if (this.destination.equals(anotherRoute.getOrigin())) {
            resultingRoute = new Route(this);
            resultingRoute.setDestination(anotherRoute.getDestination());
            resultingRoute.addWaypoint(this.destination);
            resultingRoute.addWaypoints(anotherRoute.getWaypoints());
        } else if (this.origin.equals(anotherRoute.getDestination())) {
            resultingRoute = new Route(anotherRoute);
            resultingRoute.setDestination(this.destination);
            resultingRoute.addWaypoint(this.origin);
            resultingRoute.addWaypoints(this.waypoints);
        } else if (this.origin.equals(anotherRoute.getOrigin())) {
            resultingRoute = new Route(this);
            resultingRoute.setOrigin(this.destination);
            resultingRoute.setDestination(anotherRoute.getDestination());
            resultingRoute.addWaypoint(this.origin);
            resultingRoute.addWaypoints(anotherRoute.getWaypoints());
        } else if (this.destination.equals(anotherRoute.getDestination())) {
            resultingRoute = new Route(this);
            resultingRoute.setDestination(anotherRoute.getOrigin());
            resultingRoute.addWaypoint(this.destination);
            resultingRoute.addWaypoints(anotherRoute.getWaypoints());
        } else {
            resultingRoute = new Route(this);
            resultingRoute.setDestination(anotherRoute.getDestination());
            resultingRoute.addWaypoints(new LinkedHashSet<>(Arrays.asList(this.destination, anotherRoute.getOrigin())));
            resultingRoute.addWaypoints(anotherRoute.getWaypoints());
        }

        return resultingRoute;
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

    public static Route chainRoutes(LinkedHashSet<Route> routes) {
        /*todo add google routes api call for optimization
               TSP problem without returning salesman home
        */
        if(routes.isEmpty())
            return null;

        Iterator<Route> it = routes.iterator();
        Route resultingRoute = new Route(it.next());

        while(it.hasNext()) {
            resultingRoute = resultingRoute.chainWithRoute(it.next());
        }

        return resultingRoute;
    }
}

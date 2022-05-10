package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;

import java.util.ArrayList;
import java.util.List;

public class RouteSplitter {
    public static List<Route> splitRoute(Route route) throws IdenticalCoordinatesException {
        if(route == null)
            throw new NullPointerException("Arguments can't be null");

        List<Route> dividedRoute = new ArrayList<>();
        List<Coordinates> waypoints = new ArrayList<>(route.getWaypoints());

        for(int i = 0; i < (route.getWaypoints().size()/23) + 1; i++) {
            int fromIndex = 23*i;
            int toIndex = 23 * (i+1);

            if(toIndex > route.getWaypoints().size())
                toIndex = route.getWaypoints().size();

            List<Coordinates> waypointsSubList = new ArrayList<>();

            if(i == 0)
                waypointsSubList.add(route.getOrigin());

            waypointsSubList.addAll(waypoints.subList(fromIndex, toIndex));

            if(i == route.getWaypoints().size()/23)
                waypointsSubList.add(route.getDestination());

            dividedRoute.add(new Route(waypointsSubList.get(0),
                    waypointsSubList.get(waypointsSubList.size() - 1),
                    waypointsSubList.subList(1,waypointsSubList.size() - 1)
                )
            );
        }

        return dividedRoute;
    }

    public static List<List<RouteStep>> splitRouteSteps(Route route) {
        if(route == null)
            throw new NullPointerException("Arguments can't be null");

        List<List<RouteStep>> dividedSteps = new ArrayList<>();

        for(int i = 0; i < (route.getRouteSteps().size()/10) + 1; i++) {
            int fromIndex = i * 10;
            int toIndex = (i + 1) * 10;

            if(toIndex > route.getRouteSteps().size())
                toIndex = route.getRouteSteps().size();

            List<RouteStep> stepsSublist = new ArrayList<>(route.getRouteSteps().subList(fromIndex, toIndex));
            dividedSteps.add(stepsSublist);
        }

        return dividedSteps;
    }
}

package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


public class RouteInfoFinder {
    public static void findRouteInfo(Route route, Boolean optimize, HashSet<LocalDateTime> timeStamps){
        if(route.getWaypoints().size() > 23)
            HereWaypointOptimization.optimizeWaypoints(route);

        GoogleRouteStepFinder.findRouteSteps(route, optimize);
        GoogleElevationFinder.findRouteWaypointsElevations(route);
        if(!timeStamps.isEmpty()) {
            GoogleTravelTimeFinder.FindRouteStepsTravelTime(route, timeStamps);
        }

    }

    public static void findCoordinatesElevationInfo(List<Coordinates> coordinatesList) {
        GoogleElevationFinder.findCoordinatesElevation(coordinatesList);
    }
}

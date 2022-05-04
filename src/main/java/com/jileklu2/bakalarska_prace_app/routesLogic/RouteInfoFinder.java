package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;

import java.time.LocalDateTime;
import java.util.HashSet;


public class RouteInfoFinder {
    public static void findRouteInfo(Route route, Boolean optimize, HashSet<LocalDateTime> timeStamps){
        GoogleRouteStepFinder.findRouteSteps(route, optimize);
        GoogleElevationFinder.findRouteWaypointsElevations(route);
        if(!timeStamps.isEmpty())
            GoogleTravelTimeFinder.FindRouteStepsTravelTime(route, timeStamps);
    }
}

package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.mapObjects.Route;


public class RouteInfoFinder {
    public static void findRouteInfo(Route route, Boolean optimize){
        GoogleRouteStepFinder.findRouteSteps(route, optimize);
        MapBoxTrafficDensityFinder.FindRouteTrafficDensity(route);
    }
}

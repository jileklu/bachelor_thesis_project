package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GoogleTravelTimeFinder {
    public static void FindRouteStepsTravelTime(Route route, HashSet<LocalDateTime> timeStamps) {
        System.out.println("Travel time Google");

        List<Coordinates> origins = new ArrayList<>();
        List<Coordinates> destinations = new ArrayList<>();

        for(RouteStep routeStep : route.getRouteSteps() ) {
            origins.add(routeStep.getOrigin());
            destinations.add(routeStep.getDestination());
        }

        //todo
    }
}

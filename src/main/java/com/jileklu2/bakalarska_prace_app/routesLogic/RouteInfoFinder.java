package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


public class RouteInfoFinder {
    public static void findRouteInfo(Route route, Boolean optimize, HashSet<LocalDateTime> timeStamps)
    throws CoordinatesOutOfBoundsException, IdenticalCoordinatesException, DistanceOutOfBoundsException,
    DurationOutOfBoundsException, EmptyTimeStampsSetException, EmptyDestinationsListException, InterruptedException,
    CreatedException, RequestDeniedException, OverDailyLimitException, LocationNotFoundException, UnknownStatusException,
    RouteLengthExceededException, OverQueryLimitException, WaypointsNumberExceededException, ZeroResultsException,
    InvalidRequestException, DataNotAvailableException {
        if(route.getWaypoints().size() > 23)
            HereWaypointOptimization.optimizeWaypoints(route);

        GoogleRouteStepFinder.findRouteSteps(route, optimize);

        GoogleElevationFinder.findRouteWaypointsElevations(route);

        if(!timeStamps.isEmpty())
            GoogleAvgTravelTimeFinder.findRouteStepsAvgTravelTime(route, timeStamps);

    }

    public static void findCoordinatesElevationInfo(List<Coordinates> coordinatesList)
    throws RouteLengthExceededException, RequestDeniedException, OverDailyLimitException, OverQueryLimitException,
    WaypointsNumberExceededException, ZeroResultsException, InvalidRequestException, DataNotAvailableException,
    LocationNotFoundException, UnknownStatusException {
        GoogleElevationFinder.findCoordinatesElevation(coordinatesList);
    }
}

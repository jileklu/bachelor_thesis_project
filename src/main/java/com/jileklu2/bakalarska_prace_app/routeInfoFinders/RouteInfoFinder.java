package com.jileklu2.bakalarska_prace_app.routeInfoFinders;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

/**
 *
 */
public class RouteInfoFinder {
    /**
     *
     * @param route
     * @param optimize
     * @param timeStamps
     * @throws CoordinatesOutOfBoundsException
     * @throws IdenticalCoordinatesException
     * @throws DistanceOutOfBoundsException
     * @throws DurationOutOfBoundsException
     * @throws EmptyTimeStampsSetException
     * @throws EmptyDestinationsListException
     * @throws InterruptedException
     * @throws CreatedException
     * @throws RequestDeniedException
     * @throws OverDailyLimitException
     * @throws LocationNotFoundException
     * @throws UnknownStatusException
     * @throws RouteLengthExceededException
     * @throws OverQueryLimitException
     * @throws WaypointsNumberExceededException
     * @throws ZeroResultsException
     * @throws InvalidRequestException
     * @throws DataNotAvailableException
     */
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

        if(timeStamps.isEmpty())
            timeStamps.add(LocalDateTime.now());

        GoogleAvgTravelTimeFinder.findRouteStepsAvgTravelTime(route, timeStamps);

    }

    /**
     *
     * @param coordinatesList
     * @throws RouteLengthExceededException
     * @throws RequestDeniedException
     * @throws OverDailyLimitException
     * @throws OverQueryLimitException
     * @throws WaypointsNumberExceededException
     * @throws ZeroResultsException
     * @throws InvalidRequestException
     * @throws DataNotAvailableException
     * @throws LocationNotFoundException
     * @throws UnknownStatusException
     */
    public static void findCoordinatesElevationInfo(List<Coordinates> coordinatesList)
    throws RouteLengthExceededException, RequestDeniedException, OverDailyLimitException, OverQueryLimitException,
    WaypointsNumberExceededException, ZeroResultsException, InvalidRequestException, DataNotAvailableException,
    LocationNotFoundException, UnknownStatusException {
        GoogleElevationFinder.findCoordinatesElevation(coordinatesList);
    }
}

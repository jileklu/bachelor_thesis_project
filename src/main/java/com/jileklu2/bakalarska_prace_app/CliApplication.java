package com.jileklu2.bakalarska_prace_app;

import com.jileklu2.bakalarska_prace_app.cli.MainCliController;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;


public class CliApplication {

    /**
     *
     * @param args
     * @throws OverDailyLimitException
     * @throws OverQueryLimitException
     * @throws EmptyTimeStampsSetException
     * @throws WaypointsNumberExceededException
     * @throws CoordinatesOutOfBoundsException
     * @throws EmptyDestinationsListException
     * @throws DurationOutOfBoundsException
     * @throws InterruptedException
     * @throws LocationNotFoundException
     * @throws RouteLengthExceededException
     * @throws IdenticalCoordinatesException
     * @throws CreatedException
     * @throws RequestDeniedException
     * @throws DistanceOutOfBoundsException
     * @throws ZeroResultsException
     * @throws InvalidRequestException
     * @throws DataNotAvailableException
     * @throws UnknownStatusException
     */
    public static void main(String[] args) throws OverDailyLimitException, OverQueryLimitException,
    EmptyTimeStampsSetException, WaypointsNumberExceededException, CoordinatesOutOfBoundsException,
    EmptyDestinationsListException, DurationOutOfBoundsException, InterruptedException, LocationNotFoundException,
    RouteLengthExceededException, IdenticalCoordinatesException, CreatedException, RequestDeniedException,
    DistanceOutOfBoundsException, ZeroResultsException, InvalidRequestException, DataNotAvailableException, UnknownStatusException {
        MainCliController controller = new MainCliController(args);
        controller.start();
        controller.stop();
    }
}

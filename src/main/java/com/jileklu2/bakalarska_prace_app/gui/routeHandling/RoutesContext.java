package com.jileklu2.bakalarska_prace_app.gui.routeHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.BlankScriptNameStringException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.AverageSpeedOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import javafx.util.Pair;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashSet;

public interface RoutesContext {
    void setDefaultRoute(Route defaultRoute) throws JSONException;

    void setTimeStamps(HashSet<LocalDateTime> timeStamps);

    Route getDefaultRoute() throws DefaultRouteNotSetException;

    void findRouteInfo(Route route) throws IdenticalCoordinatesException, DistanceOutOfBoundsException,
        EmptyTimeStampsSetException, CoordinatesOutOfBoundsException,
        EmptyDestinationsListException, DurationOutOfBoundsException, InterruptedException, RouteLengthExceededException, CreatedException, RequestDeniedException, OverDailyLimitException, OverQueryLimitException, WaypointsNumberExceededException, ZeroResultsException, InvalidRequestException, DataNotAvailableException, LocationNotFoundException, UnknownStatusException;

    void loadJsonRoute(String path) throws FileNotFoundException, IdenticalCoordinatesException,
        CoordinatesOutOfBoundsException, DistanceOutOfBoundsException,
        EmptyTimeStampsSetException, EmptyDestinationsListException,
        DurationOutOfBoundsException, InterruptedException, RouteLengthExceededException, CreatedException, RequestDeniedException, OverDailyLimitException, OverQueryLimitException, WaypointsNumberExceededException, ZeroResultsException, InvalidRequestException, DataNotAvailableException, LocationNotFoundException, UnknownStatusException;

    int getDefaultRouteStepIndex(RouteStep routeStep) throws DefaultRouteNotSetException;

    int getDefaultRouteStepsNum() throws DefaultRouteNotSetException;

    RouteStep getDefaultRouteStepOnIndex(int index) throws DefaultRouteNotSetException;

    void changeRouteCoordinates(RouteStep routeStep, Pair<Coordinates, Coordinates> coordinatesPair)
        throws CoordinatesOutOfBoundsException, DefaultRouteNotSetException;

    void findRouteStepInfo(RouteStep routeStep) throws IdenticalCoordinatesException, DistanceOutOfBoundsException,
        DurationOutOfBoundsException, AverageSpeedOutOfBoundsException,
        EmptyTimeStampsSetException, CoordinatesOutOfBoundsException,
        EmptyDestinationsListException, DefaultRouteNotSetException,
        InterruptedException, RouteLengthExceededException, CreatedException, RequestDeniedException, OverDailyLimitException, OverQueryLimitException, WaypointsNumberExceededException, ZeroResultsException, InvalidRequestException, DataNotAvailableException, LocationNotFoundException, UnknownStatusException;

    void setMapViewContext(MapViewContext mapViewContext);

    void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext);

    void collectNewWaypoint(String waypoint) throws CoordinatesOutOfBoundsException, IdenticalCoordinatesException,
        DistanceOutOfBoundsException, EmptyTimeStampsSetException,
        EmptyDestinationsListException, DurationOutOfBoundsException,
        DefaultRouteNotSetException, BlankScriptNameStringException,
        InterruptedException, RouteLengthExceededException, CreatedException, RequestDeniedException, OverDailyLimitException, OverQueryLimitException, WaypointsNumberExceededException, ZeroResultsException, InvalidRequestException, DataNotAvailableException, LocationNotFoundException, UnknownStatusException;

    void collectChangedMarker(String marker) throws CoordinatesOutOfBoundsException, IdenticalCoordinatesException,
        DistanceOutOfBoundsException, AverageSpeedOutOfBoundsException,
        DurationOutOfBoundsException, EmptyTimeStampsSetException,
        EmptyDestinationsListException, DefaultRouteNotSetException,
        BlankScriptNameStringException, InterruptedException, RouteLengthExceededException, CreatedException, RequestDeniedException, OverDailyLimitException, OverQueryLimitException, WaypointsNumberExceededException, ZeroResultsException, InvalidRequestException, DataNotAvailableException, LocationNotFoundException, UnknownStatusException;

    void collectCoordinates(String coordinates) throws CoordinatesOutOfBoundsException, IdenticalCoordinatesException,
        DistanceOutOfBoundsException, EmptyTimeStampsSetException,
        EmptyDestinationsListException, DurationOutOfBoundsException,
        DefaultRouteNotSetException, BlankScriptNameStringException,
        InterruptedException, RouteLengthExceededException, CreatedException, RequestDeniedException, OverDailyLimitException, OverQueryLimitException, WaypointsNumberExceededException, ZeroResultsException, InvalidRequestException, DataNotAvailableException, LocationNotFoundException, UnknownStatusException;
}

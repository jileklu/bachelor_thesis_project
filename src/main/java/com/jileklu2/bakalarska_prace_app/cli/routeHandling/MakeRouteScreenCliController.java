package com.jileklu2.bakalarska_prace_app.cli.routeHandling;

import com.jileklu2.bakalarska_prace_app.cli.MainCliController;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routeInfoFinders.RouteInfoFinder;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class MakeRouteScreenCliController {

    private final MainCliController mainController;

    private final MakeRouteScreenCliView view;

    public MakeRouteScreenCliController(MainCliController mainController) {
        this.mainController = mainController;
        this.view = new MakeRouteScreenCliView();
    }

    /**
     *
     * @throws IdenticalCoordinatesException
     * @throws CoordinatesOutOfBoundsException
     * @throws OverDailyLimitException
     * @throws OverQueryLimitException
     * @throws EmptyTimeStampsSetException
     * @throws WaypointsNumberExceededException
     * @throws EmptyDestinationsListException
     * @throws DurationOutOfBoundsException
     * @throws InterruptedException
     * @throws LocationNotFoundException
     * @throws RouteLengthExceededException
     * @throws CreatedException
     * @throws RequestDeniedException
     * @throws DistanceOutOfBoundsException
     * @throws ZeroResultsException
     * @throws InvalidRequestException
     * @throws DataNotAvailableException
     * @throws UnknownStatusException
     */
    public void getRoute() throws IdenticalCoordinatesException, CoordinatesOutOfBoundsException,
    OverDailyLimitException, OverQueryLimitException, EmptyTimeStampsSetException, WaypointsNumberExceededException,
    EmptyDestinationsListException, DurationOutOfBoundsException, InterruptedException, LocationNotFoundException,
    RouteLengthExceededException, CreatedException, RequestDeniedException, DistanceOutOfBoundsException,
    ZeroResultsException, InvalidRequestException, DataNotAvailableException, UnknownStatusException {
        Route route;
        view.showMessage("Please enter origin:");
        Coordinates origin = getCoordinates();
        view.showMessage("Please enter destination:");
        Coordinates destination = getCoordinates();
        LinkedHashSet<Coordinates> waypoints = getWaypoints();

        route = new Route(origin, destination, waypoints);

        RouteInfoFinder.findRouteInfo(route, true, new HashSet<>());

        RouteCliHandler.setCurrentRoute(route);
    }

    /**
     *
     * @return
     */
    private LinkedHashSet<Coordinates> getWaypoints() {
        LinkedHashSet<Coordinates> waypoints = new LinkedHashSet<>();
        int selection;
        while(true) {
            selection = getSelection();
            switch(selection) {
                case 1:
                    waypoints.add(getCoordinates());
                    break;
                case 2:
                    return waypoints;
                default:
                    view.showMessage("Not existing choice, please write numbers of given choices only.");
            }
        }
    }

    /**
     *
     * @return
     */
    private int getSelection() {
        Scanner scanner;
        int selection;
        while(true) {
            scanner = mainController.getScanner();
            view.showWaypointsGetterText();
            try {
                selection = scanner.nextInt();
                break;
            } catch (java.util.InputMismatchException e) {
                view.showMessage("Please enter only integers.");
            }
        }
        return selection;
    }

    /**
     *
     * @return
     */
    private Coordinates getCoordinates() {
        Scanner scanner;
        double lat;
        double lng;
        Coordinates coordinates;
        while(true) {
            scanner = mainController.getScanner();
            try {
                view.showMessage("lat:");
                lat = scanner.nextDouble();
            }catch (java.util.InputMismatchException e) {
                view.showMessage("Please enter coordinates in the correct format.");
                continue;
            }

            try {
                view.showMessage("lng:");
                lng = scanner.nextDouble();
            }catch (java.util.InputMismatchException e) {
                view.showMessage("Please enter coordinates in the correct format.");
                continue;
            }

            try {
                coordinates = new Coordinates(lat, lng);
                break;
            }catch (CoordinatesOutOfBoundsException e) {
                view.showMessage("Please enter valid coordinates.");
            }
        }
        return coordinates;
    }
}

package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.cli.routeHandling.RouteCliHandler;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;

import java.util.Scanner;

/**
 *
 */
public class MainMenuCliController {

    private final MainCliController mainController;

    private final MainMenuCliView view;

    public MainMenuCliController(MainCliController mainController) {
        this.mainController = mainController;
        this.view = new MainMenuCliView();
    }

    public void showMenu() throws OverDailyLimitException, OverQueryLimitException,
    EmptyTimeStampsSetException, WaypointsNumberExceededException, CoordinatesOutOfBoundsException,
    EmptyDestinationsListException, DurationOutOfBoundsException, InterruptedException, LocationNotFoundException,
    RouteLengthExceededException, IdenticalCoordinatesException, CreatedException, RequestDeniedException,
    DistanceOutOfBoundsException, ZeroResultsException, InvalidRequestException, DataNotAvailableException,
    UnknownStatusException {
        while(true) {
            int selection = getSelection();
            switch(selection) {
                case 1:
                    mainController.showMakeRouteScreen();
                    break;
                case 2:
                    if(RouteCliHandler.getCurrentRoute() == null) {
                        view.showMessage("Route was not created yet.");
                    } else {
                        view.showMessage(RouteCliHandler.getCurrentRoute().toJSON().toString(2));
                    }
                    break;
                case 3:
                    mainController.showMakeRouteImportScreen();
                    break;
                case 4:
                    if(RouteCliHandler.getCurrentRoute() == null) {
                        view.showMessage("Route was not created yet.");
                    } else {
                        mainController.showMakeRouteExportScreen();
                    }
                    break;
                case 0:
                    return;
                default:
                    view.showMessage("Not existing choice, please write numbers of given choices only.");
            }
        }
    }

    private int getSelection() {
        Scanner scanner;
        int selection;
        while(true) {
            scanner = mainController.getScanner();
            view.showMenuText();
            try {
                selection = scanner.nextInt();
                break;
            } catch (java.util.InputMismatchException e) {
                view.showMessage("Please enter only integers.");
            }
        }
        return selection;
    }
}

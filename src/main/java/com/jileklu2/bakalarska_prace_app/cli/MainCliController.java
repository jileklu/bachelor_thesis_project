package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.handlers.arguments.ArgumentsCliHandler;
import com.jileklu2.bakalarska_prace_app.cli.fileHandling.RouteExportCliController;
import com.jileklu2.bakalarska_prace_app.cli.fileHandling.RouteImportCliController;
import com.jileklu2.bakalarska_prace_app.cli.routeHandling.MakeRouteScreenCliController;
import com.jileklu2.bakalarska_prace_app.cli.routeHandling.RouteCliHandler;
import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Scanner;

import static com.jileklu2.bakalarska_prace_app.handlers.arguments.ArgumentType.*;

/**
 *
 */
public class MainCliController {
    private ArgumentsCliHandler argumentsHandler;
    private MainMenuCliController mainMenu;
    private RouteImportCliController routeImportScreen;
    private MakeRouteScreenCliController makeRouteScreen;
    private RouteExportCliController routeExportScreen;
    private Scanner scanner;

    /**
     *
     * @param args
     */
    public MainCliController(String[] args) {
        this.argumentsHandler = new ArgumentsCliHandler(args);
        this.mainMenu = new MainMenuCliController(this);
        this.makeRouteScreen = new MakeRouteScreenCliController(this);
        this.routeImportScreen = new RouteImportCliController(this);
        this.routeExportScreen = new RouteExportCliController(this);
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
    }

    /**
     *
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
    public void start() throws OverDailyLimitException, OverQueryLimitException, EmptyTimeStampsSetException,
    WaypointsNumberExceededException, CoordinatesOutOfBoundsException, EmptyDestinationsListException,
    DurationOutOfBoundsException, InterruptedException, LocationNotFoundException, RouteLengthExceededException,
    IdenticalCoordinatesException, CreatedException, RequestDeniedException, DistanceOutOfBoundsException,
    ZeroResultsException, InvalidRequestException, DataNotAvailableException, UnknownStatusException {

        if(this.argumentsHandler.getArgValues().containsKey(IN_FILE)) {
            try{
                JSONObject routeJson = FileHandler.readJsonFile(
                    String.valueOf(this.argumentsHandler.getArgValues().get(IN_FILE))
                );

                RouteCliHandler.setCurrentRoute(routeJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(this.argumentsHandler.getArgValues().containsKey(MENU_ON)) {
            mainMenu.showMenu();
        }

        if(this.argumentsHandler.getArgValues().containsKey(OUT_FILE)) {
            FileHandler.createJsonFile(
                String.valueOf(this.argumentsHandler.getArgValues().get(OUT_FILE)),
                RouteCliHandler.getCurrentRoute().toJSON()
            );
        }
   }

    /**
     *
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
   public void showMakeRouteScreen() throws OverDailyLimitException, OverQueryLimitException,
   EmptyTimeStampsSetException, WaypointsNumberExceededException, CoordinatesOutOfBoundsException,
   EmptyDestinationsListException, DurationOutOfBoundsException, InterruptedException, LocationNotFoundException,
   RouteLengthExceededException, IdenticalCoordinatesException, CreatedException, RequestDeniedException,
   DistanceOutOfBoundsException, ZeroResultsException, InvalidRequestException, DataNotAvailableException,
   UnknownStatusException {
       makeRouteScreen.getRoute();
   }

    /**
     *
     * @param route
     */
   public void setCurrentRoute(Route route) {
        RouteCliHandler.setCurrentRoute(route.toJSON());
   }

    /**
     *
     * @param route
     */
    public void setCurrentRoute(JSONObject route) {
        RouteCliHandler.setCurrentRoute(route);
    }

    /**
     *
     */
   public void showMakeRouteImportScreen() {
        routeImportScreen.getRoute();
   }

    /**
     *
     */
   public void showMakeRouteExportScreen() {
        routeExportScreen.exportRoute();
   }

    /**
     *
     * @return
     */
   public Scanner getScanner(){return new Scanner(System.in).useLocale(Locale.US);}

    /**
     *
     */
    public void stop(){scanner.close();}
}

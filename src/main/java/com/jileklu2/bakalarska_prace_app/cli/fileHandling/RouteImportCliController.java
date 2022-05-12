package com.jileklu2.bakalarska_prace_app.cli.fileHandling;

import com.jileklu2.bakalarska_prace_app.cli.MainCliController;
import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Route import CLI controller class
 */
public class RouteImportCliController {
    private final MainCliController mainController;

    private final RouteImportCliView view;

    /**
     *
     * @param mainController Main controller reference
     */
    public RouteImportCliController(MainCliController mainController) {
        this.mainController = mainController;
        this.view = new RouteImportCliView();
    }

    /**
     *
     * Get route from the user
     */
    public void getRoute() {
        JSONObject routeJson = getRouteJson();
        mainController.setCurrentRoute(routeJson);
        view.showMessage("Import successful.");
    }

    /**
     * Get route from the JSON file
     *
     * @return JSON object of the collected route
     */
    private JSONObject getRouteJson() {
        Scanner scanner;
        JSONObject routeJson;
        while(true) {
            scanner = mainController.getScanner();
            try {
                view.showMessage("Please enter file path:");
                String path = scanner.next();

                if(path.length() < 5) {
                    throw new IllegalArgumentException("Please enter the path in the correct format.");
                }
                routeJson = FileHandler.readJsonFile(Paths.get(path).toString());
                return routeJson;
            }catch (NullPointerException | IllegalArgumentException e) {
                view.showMessage("Please enter the path in the correct format.");
            } catch (FileNotFoundException e) {
                view.showMessage("Please enter existing path");
            }

        }

    }

}

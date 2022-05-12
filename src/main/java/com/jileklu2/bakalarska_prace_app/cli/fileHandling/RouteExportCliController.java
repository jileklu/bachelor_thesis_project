package com.jileklu2.bakalarska_prace_app.cli.fileHandling;

import com.jileklu2.bakalarska_prace_app.builders.GpxBuilder;
import com.jileklu2.bakalarska_prace_app.cli.MainCliController;
import com.jileklu2.bakalarska_prace_app.cli.routeHandling.RouteCliHandler;
import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Controller class of CLI route export screen
 */
public class RouteExportCliController {
    private final MainCliController mainController;

    private final RouteExportCliView view;

    /**
     * Constructor
     *
     * @param mainController Main controller reference
     */
    public RouteExportCliController(MainCliController mainController) {
        this.mainController = mainController;
        this.view = new RouteExportCliView();
    }

    /**
     * Method for creating file with use of FileHandler class
     */
    public void exportRoute(){

        Path filePath = getFilePath();
/*
        FileHandler.createJsonFile(
            String.valueOf(filePath),
            RouteHandlerCli.getCurrentRoute().toJSON()
        );
*/
        FileHandler.createGpxFile(
            String.valueOf(filePath),
            GpxBuilder.buildRouteGpx(RouteCliHandler.getCurrentRoute())
        );

        System.out.println("Export successful.");
    }

    /**
     *
     * @return Route file path
     */
    private Path getFilePath() {
        Scanner scanner;
        String path;
        Path filePath;
        while(true) {
            view.showMessage("Please enter file path:");
            scanner = mainController.getScanner();
            try {
                path = scanner.nextLine();

                if(path.length() < 5) {
                    throw new IllegalArgumentException("Please enter the path in the correct format.");
                }

                filePath = Paths.get(path);
                return filePath;
            }catch (NullPointerException | IllegalArgumentException e) {
                view.showMessage("Please enter the path in the correct format.");
            }
        }
    }
}

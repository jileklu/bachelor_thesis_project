package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.builders.GpxBuilder;
import com.jileklu2.bakalarska_prace_app.cli.route.RouteHandlerCli;
import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class RouteExportConsoleScreen {
    private final ControllerCli controller;
    public RouteExportConsoleScreen(ControllerCli controller) {
        this.controller = controller;
    }
    public void exportRoute(){
        System.out.println("Please enter file path:");
        Path filePath = getFilePath();
/*
        FileHandler.createJsonFile(
            String.valueOf(filePath),
            RouteHandlerCli.getCurrentRoute().toJSON()
        );
*/
        FileHandler.createGpxFile(
            String.valueOf(filePath),
            GpxBuilder.buildRouteGpx(RouteHandlerCli.getCurrentRoute())
        );

        System.out.println("Export successful.");
    }

    private Path getFilePath() {
        controller.resetScanner();
        Scanner scanner = controller.getScanner();
        String path;
        Path filePath;
        while(true) {
            try {
                path = scanner.nextLine();

                if(path.length() < 5) {
                    throw new IllegalArgumentException("Please enter the path in the correct format.");
                }

                filePath = Paths.get(path);
                break;
            }catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("Please enter the path in the correct format.");
                scanner.next();
            }
        }
        return filePath;
    }
}

package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

public class RouteImportConsoleScreen {
    private final ControllerCli controller;

    public RouteImportConsoleScreen(ControllerCli controller) {
        this.controller = controller;
    }

    public void getRoute() {
        System.out.println("Please enter file path:");
        JSONObject routeJson = getRouteJson();
        controller.setCurrentRoute(routeJson);
    }

    private JSONObject getRouteJson() {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        JSONObject routeJson;
        while(true) {
            try {
                String path = scanner.next();

                if(path.length() < 5) {
                    throw new IllegalArgumentException("Please enter the path in the correct format.");
                }
                routeJson = FileHandler.readJsonFile(Paths.get(path).toString());
                break;
            }catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("Please enter the path in the correct format.");
                scanner.next();
            } catch (FileNotFoundException e) {
                System.out.println("Please enter existing path");
                scanner.next();
            }
        }

        scanner.close();
        return routeJson;
    }

}

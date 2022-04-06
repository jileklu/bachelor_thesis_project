package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.mapObjects.RouteStep;

import java.util.Scanner;

public class MainConsoleScreen {

    private static Route createdRoute = null;

    public static void launchMenu() {
        while(true) {
            int selection = getSelection();
            switch(selection) {
                case 1:
                    createdRoute = RouteMakerConsoleScreen.getRoute();
                    break;
                case 2:
                    if(createdRoute == null) {
                        System.out.println("Route was not created yet.");
                    } else {
                        for(RouteStep routeStep : createdRoute.getRouteSteps()) {
                            System.out.println(routeStep.toFormattedString());
                        }
                    }
                    break;
                case 3:
                    System.out.println("Import selected");
                    break;
                case 4:
                    System.out.println("Export selected");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Not existing choice, please write numbers of given choices only.");
            }
        }
    }

    private static void showMenuText() {
        System.out.println("Select Action:");
        System.out.println("[1] Make Route");
        System.out.println("[2] Print Route Steps");
        System.out.println("[3] Import Route");
        System.out.println("[4] Export Route");
        System.out.println("[0] Exit");
    }

    private static int getSelection() {
        Scanner scanner = new Scanner(System.in);
        int selection;
        while(true) {
            showMenuText();
            try {
                selection = scanner.nextInt();
                break;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Please enter only integers.");
                scanner.next();
            }
        }
        return selection;
    }
}

package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.cli.route.RouteHandlerCli;

import java.util.Scanner;

public class ConsoleMainMenu {

    private final ControllerCli controller;

    public ConsoleMainMenu(ControllerCli createdRoute) {
        this.controller = createdRoute;
    }

    public void showMenu(){
        while(true) {
            int selection = getSelection();
            switch(selection) {
                case 1:
                    controller.showMakeRouteScreen();
                    break;
                case 2:
                    if(RouteHandlerCli.getCurrentRoute() == null) {
                        System.out.println("Route was not created yet.");
                    } else {
                        System.out.println(RouteHandlerCli.getCurrentRoute().toJSON().toString(2));
                    }
                    break;
                case 3:
                    controller.showMakeRouteImportScreen();
                    break;
                case 4:
                    if(RouteHandlerCli.getCurrentRoute() == null) {
                        System.out.println("Route was not created yet.");
                    } else {
                        controller.showMakeRouteExportScreen();
                    }
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
        System.out.println("[2] Print Route JSON");
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

        scanner.close();
        return selection;
    }
}

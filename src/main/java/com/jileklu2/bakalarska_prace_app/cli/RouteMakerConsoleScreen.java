package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.RouteInfoFinder;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Scanner;

public class RouteMakerConsoleScreen {
    public static Route getRoute() {
        Route route;
        System.out.println("Please enter origin:");
        Coordinates origin = getCoordinates();
        System.out.println("Please enter destination:");
        Coordinates destination = getCoordinates();
        LinkedHashSet<Coordinates> waypoints = getWaypoints();

        route = new Route(origin, destination, waypoints);
        RouteInfoFinder.findRouteInfo(route, true);

        return route;
    }

    private static LinkedHashSet<Coordinates> getWaypoints() {
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
                    System.out.println("Not existing choice, please write numbers of given choices only.");
            }
        }
    }

    private static void showWaypointsGetterText() {
        System.out.println("Do you want to add route waypoint?");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
    }

    private static int getSelection() {
        Scanner scanner = new Scanner(System.in);
        int selection;
        while(true) {
            showWaypointsGetterText();
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

    private static Coordinates getCoordinates() {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        Double lat;
        Double lng;
        Coordinates coordinates;
        while(true) {
            try {
                System.out.println("lat:");
                 lat = scanner.nextDouble();
            }catch (java.util.InputMismatchException e) {
                System.out.println("Please enter coordinates in the correct format.");
                scanner.next();
                continue;
            }

            try {
                System.out.println("lng:");
                lng = scanner.nextDouble();
            }catch (java.util.InputMismatchException e) {
                System.out.println("Please enter coordinates in the correct format.");
                scanner.next();
                continue;
            }

            try {
                coordinates = new Coordinates(lat, lng);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Coordinates out of boundaries.");
            }
        }

        return coordinates;
    }
}

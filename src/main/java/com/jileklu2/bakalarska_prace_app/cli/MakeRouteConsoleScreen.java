package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.RouteInfoFinder;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class MakeRouteConsoleScreen {

    private final ControllerCli controller;

    public MakeRouteConsoleScreen(ControllerCli controller) {
        this.controller = controller;
    }

    public void getRoute() {
        Route route;
        System.out.println("Please enter origin:");
        Coordinates origin = getCoordinates();
        System.out.println("Please enter destination:");
        Coordinates destination = getCoordinates();
        LinkedHashSet<Coordinates> waypoints = getWaypoints();

        try {
            route = new Route(origin, destination, waypoints);
        } catch (IdenticalCoordinatesException e) {
            throw new RuntimeException(e);
        }
        try {
            RouteInfoFinder.findRouteInfo(route, true, new HashSet<>());
        } catch (CoordinatesOutOfBoundsException | EmptyDestinationsListException | EmptyTimeStampsSetException |
                 DurationOutOfBoundsException | DistanceOutOfBoundsException | IdenticalCoordinatesException |
                 InterruptedException e) {
            //todo
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        controller.setCurrentRoute(route);
    }

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
                    System.out.println("Not existing choice, please write numbers of given choices only.");
            }
        }
    }

    private void showWaypointsGetterText() {
        System.out.println("Do you want to add route waypoint?");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
    }

    private int getSelection() {
        controller.resetScanner();
        Scanner scanner = controller.getScanner();
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

    private Coordinates getCoordinates() {
        controller.resetScanner();
        Scanner scanner = controller.getScanner();
        double lat;
        double lng;
        Double elevation;
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
            }catch (CoordinatesOutOfBoundsException e) {
                throw new RuntimeException(e);
            }
        }
        return coordinates;
    }
}

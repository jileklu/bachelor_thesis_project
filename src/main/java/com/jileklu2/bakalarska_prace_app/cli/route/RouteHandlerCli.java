package com.jileklu2.bakalarska_prace_app.cli.route;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.RouteInfoFinder;
import org.json.JSONObject;

import java.util.HashSet;

public class RouteHandlerCli {
    private static Route currentRoute = null;

    public static void setCurrentRoute(JSONObject jsonObject) {
        //todo
        try {
            currentRoute = new Route(jsonObject);
        } catch (CoordinatesOutOfBoundsException e) {
            throw new RuntimeException(e);
        } catch (IdenticalCoordinatesException e) {
            throw new RuntimeException(e);
        }
        try {
            RouteInfoFinder.findRouteInfo(currentRoute, true, new HashSet<>());
        } catch (CoordinatesOutOfBoundsException e) {
            throw new RuntimeException(e);
        } catch (IdenticalCoordinatesException e) {
            throw new RuntimeException(e);
        } catch (DistanceOutOfBoundsException e) {
            throw new RuntimeException(e);
        } catch (DurationOutOfBoundsException e) {
            throw new RuntimeException(e);
        } catch (EmptyTimeStampsSetException e) {
            throw new RuntimeException(e);
        } catch (EmptyDestinationsListException e) {
            throw new RuntimeException(e);
        }
    }

    public static Route getCurrentRoute() {
        return currentRoute;
    }
}

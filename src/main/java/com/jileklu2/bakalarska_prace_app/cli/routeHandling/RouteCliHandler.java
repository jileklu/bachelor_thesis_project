package com.jileklu2.bakalarska_prace_app.cli.routeHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routeInfoFinders.RouteInfoFinder;
import org.json.JSONObject;

import java.util.HashSet;

/**
 *
 */
public class RouteCliHandler {
    private static Route currentRoute = null;

    /**
     *
     * @param jsonObject
     */
    public static void setCurrentRoute(JSONObject jsonObject) {
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param route
     */
    public static void setCurrentRoute(Route route) {
        currentRoute = new Route(route);
    }

    /**
     *
     * @return
     */
    public static Route getCurrentRoute() {
        return currentRoute;
    }
}

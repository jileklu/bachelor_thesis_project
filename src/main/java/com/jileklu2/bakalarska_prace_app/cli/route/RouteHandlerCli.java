package com.jileklu2.bakalarska_prace_app.cli.route;

import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.RouteInfoFinder;
import org.json.JSONObject;

public class RouteHandlerCli {
    private static Route currentRoute = null;

    public static void setCurrentRoute(JSONObject jsonObject) {
        currentRoute = new Route(jsonObject);
        RouteInfoFinder.findRouteInfo(currentRoute, true);
    }

    public static Route getCurrentRoute() {
        return currentRoute;
    }
}

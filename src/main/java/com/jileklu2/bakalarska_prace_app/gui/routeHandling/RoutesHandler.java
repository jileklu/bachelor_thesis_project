package com.jileklu2.bakalarska_prace_app.gui.routeHandling;

import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;
import com.jileklu2.bakalarska_prace_app.routesLogic.RouteInfoFinder;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;


public class RoutesHandler implements RoutesContext {
    private Route defaultRoute;

    private HashSet<LocalDateTime> timeStamps;

    public RoutesHandler() {
        timeStamps = new HashSet<>();
    }

    @Override
    public void setDefaultRoute(Route defaultRoute){
        this.defaultRoute = defaultRoute;
    }

    @Override
    public void setTimeStamps(HashSet<LocalDateTime> timeStamps) {
        this.timeStamps = new HashSet<>(timeStamps);
    }

    @Override
    public Route getDefaultRoute() {
        return defaultRoute;
    }

    @Override
    public void findRouteInfo(Route route) {
        RouteInfoFinder.findRouteInfo(route, true, timeStamps);
    }

    @Override
    public void loadJsonRoute(String path) throws FileNotFoundException {
        JSONObject routeJson = FileHandler.readJsonFile(Paths.get(path).toString());
        Route newRoute = new Route(routeJson);
        findRouteInfo(newRoute);
        setDefaultRoute(newRoute);
    }
}

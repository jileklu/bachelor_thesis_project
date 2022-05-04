package com.jileklu2.bakalarska_prace_app.gui.routeHandling;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashSet;

public interface RoutesContext {
    void setDefaultRoute(Route defaultRoute) throws JSONException;

    void setTimeStamps(HashSet<LocalDateTime> timeStamps);
    Route getDefaultRoute();

    void findRouteInfo(Route route);

    void loadJsonRoute(String path) throws FileNotFoundException;
}

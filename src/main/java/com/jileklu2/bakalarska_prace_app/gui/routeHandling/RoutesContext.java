package com.jileklu2.bakalarska_prace_app.gui.routeHandling;

import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import javafx.util.Pair;
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

    int getDefaultRouteStepIndex(RouteStep routeStep);

    int getDefaultRouteStepsNum();

    RouteStep getDefaultRouteStepOnIndex(int index);

    void changeRouteCoordinates(RouteStep routeStep, Pair<Coordinates, Coordinates> coordinatesPair);

    void findRouteStepInfo(RouteStep routeStep);

    void setMapViewContext(MapViewContext mapViewContext);

    void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext);
}

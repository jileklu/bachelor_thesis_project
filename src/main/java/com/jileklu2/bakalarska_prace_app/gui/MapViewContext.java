package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.BlankScriptNameStringException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Marker;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;

import java.util.List;

public interface MapViewContext {
    void showRoute(Route route) throws IdenticalCoordinatesException, BlankScriptNameStringException;
    void loadMap();
    void initMap() throws BlankScriptNameStringException;
    void showDefaultRoute() throws DefaultRouteNotSetException, IdenticalCoordinatesException, BlankScriptNameStringException;
    void showMarker(Marker marker) throws BlankScriptNameStringException;
    void showMarkers(List<Marker> markers) throws BlankScriptNameStringException;
}

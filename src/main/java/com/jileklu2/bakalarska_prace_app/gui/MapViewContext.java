package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Marker;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;

import java.util.List;

public interface MapViewContext {
    void showRoute(Route route);
    void loadMap();
    void initMap();
    void showDefaultRoute();
    void showMarker(Marker marker);
    void showMarkers(List<Marker> markers);
}

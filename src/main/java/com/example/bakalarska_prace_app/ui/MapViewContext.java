package com.example.bakalarska_prace_app.ui;

import com.example.bakalarska_prace_app.mapObjects.Marker;
import com.example.bakalarska_prace_app.mapObjects.Route;

import java.util.List;

public interface MapViewContext {
    void showRoute(Route route);
    void loadMap();
    void initMap();
    void showDefaultRoute();
    void showMarker(Marker marker);
    void showMarkers(List<Marker> markers);
}

package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.scriptBuilders.JavascriptBuilder;
import com.jileklu2.bakalarska_prace_app.mapObjects.Marker;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.mapObjects.RouteStep;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapContentHandler implements MapViewContext{
    @FXML
    private WebView webView;

    private WebEngine webEngine;

    private RoutesContext routesContext;

    public MapContentHandler(WebView webView, RoutesContext routesContext){
        this.routesContext = routesContext;
        this.webView = webView;
        webEngine = this.webView.getEngine();
    }

    @Override
    public void showRoute(Route route) {
        webEngine.executeScript(JavascriptBuilder.createScriptString("viewRoute", route));
    }

    @Override
    public void loadMap() {
        URL urlGoogleMaps = getClass().getResource("google_map.html");

        assert urlGoogleMaps != null;
        webEngine.load(urlGoogleMaps.toExternalForm());
    }

    @Override
    public void initMap() {
        webEngine.executeScript(JavascriptBuilder.createScriptString("initMap"));
    }

    @Override
    public void showDefaultRoute() {
        Route defaultRoute = routesContext.getDefaultRoute();
        List<RouteStep> routeSteps = defaultRoute.getRouteSteps();
        List<Marker> markers = new ArrayList<>();
        for(int i = 0; i < (routeSteps.size() > 0 ? routeSteps.size() - 1 : 0); i++) {
            markers.add(new Marker(routeSteps.get(i).getDestination(),
                        String.valueOf(routeSteps.get(i).getStepNumber())));
        }
        showRoute(defaultRoute);
        showMarkers(markers);
    }

    @Override
    public void showMarker(Marker marker) {
        webEngine.executeScript(JavascriptBuilder.createScriptString("addMarker", marker));
    }

    @Override
    public void showMarkers(List<Marker> markers) {
        webEngine.executeScript(JavascriptBuilder.createScriptString("clearMarkers"));
        for(Marker marker : markers){
            showMarker(marker);
        }
    }
}

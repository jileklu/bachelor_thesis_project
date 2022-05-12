package com.jileklu2.bakalarska_prace_app.gui.mapHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.BlankScriptNameStringException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.builders.scriptBuilders.JavascriptBuilder;
import com.jileklu2.bakalarska_prace_app.mapObjects.splitters.RouteSplitter;
import com.jileklu2.bakalarska_prace_app.mapObjects.Marker;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.mapObjects.RouteStep;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapContentController implements MapViewContext {
    @FXML
    private WebView webView;

    private WebEngine webEngine;

    private RoutesContext routesContext;

    public MapContentController(WebView webView, RoutesContext routesContext){
        this.routesContext = routesContext;
        this.webView = webView;
        webEngine = this.webView.getEngine();
    }

    @Override
    public void showRoute(Route route) throws IdenticalCoordinatesException, BlankScriptNameStringException {
        List<Route> helpingRoutes = RouteSplitter.splitRoute(route);

        webEngine.executeScript(JavascriptBuilder.createScriptString("viewRoutes", helpingRoutes));
    }

    @Override
    public void loadMap() {
        URL urlGoogleMaps = getClass().getResource("google_map.html");

        if(urlGoogleMaps == null)
            throw new NullPointerException("Html not found.");

        webEngine.load(urlGoogleMaps.toExternalForm());
    }

    @Override
    public void initMap() throws BlankScriptNameStringException {
        webEngine.executeScript(JavascriptBuilder.createScriptString("initMap"));
    }

    @Override
    public void showDefaultRoute() throws DefaultRouteNotSetException, IdenticalCoordinatesException,
    BlankScriptNameStringException {
        Route defaultRoute = routesContext.getDefaultRoute();
        List<RouteStep> routeSteps = defaultRoute.getRouteSteps();
        List<Marker> markers = new ArrayList<>();
        markers.add(new Marker(defaultRoute.getOrigin(), "Origin"));
        for(int i = 0; i < routeSteps.size(); i++) {
            if( i == routeSteps.size() - 1) {
                markers.add(new Marker(routeSteps.get(i).getDestination(),"Destination"));
            } else  {
                markers.add(new Marker(routeSteps.get(i).getDestination(),
                    String.valueOf(routeSteps.get(i).getStepNumber())));
            }

        }
        showRoute(defaultRoute);
        showMarkers(markers);
    }

    @Override
    public void showMarker(Marker marker) throws BlankScriptNameStringException {
        webEngine.executeScript(JavascriptBuilder.createScriptString("addMarker", marker));
    }

    @Override
    public void showMarkers(List<Marker> markers) throws BlankScriptNameStringException {
        webEngine.executeScript(JavascriptBuilder.createScriptString("clearMarkers"));

        for(Marker marker : markers){
            showMarker(marker);
        }
    }
}

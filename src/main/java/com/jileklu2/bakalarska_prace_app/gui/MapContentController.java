package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.builders.scriptBuilders.JavascriptBuilder;
import com.jileklu2.bakalarska_prace_app.routesLogic.RouteSplitter;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Marker;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapContentController implements MapViewContext{
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
    public void showRoute(Route route) {
        List<Route> helpingRoutes = RouteSplitter.splitRoute(route);

        webEngine.executeScript(JavascriptBuilder.createScriptString("viewRoutes", helpingRoutes));
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

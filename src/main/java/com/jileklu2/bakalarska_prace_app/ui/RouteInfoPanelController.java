package com.jileklu2.bakalarska_prace_app.ui;

import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.mapObjects.RouteStep;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListView;

import java.util.List;

public class RouteInfoPanelController implements RouteInfoPanelContext {
    RoutesContext routesContext;

    private ListView<String> listView;

    public RouteInfoPanelController(RoutesContext routesContext, ListView<String> listView) {
        this.routesContext = routesContext;
        this.listView = listView;
        selectionModelOff();
    }

    @Override
    public void showDefaultRouteInfo() {
        Route defaultRoute = routesContext.getDefaultRoute();
        List<RouteStep> defaultRouteSteps = defaultRoute.getRouteSteps();
        for(RouteStep routeStep : defaultRouteSteps) {
            listView.getItems().add(routeStep.toFormattedString());
        }
    }

    private void selectionModelOff() {
        listView.getSelectionModel().selectedIndexProperty().addListener(
                (ChangeListener) (observable, oldvalue, newValue) ->
                        Platform.runLater(() -> listView.getSelectionModel().select(-1))
        );
    }

}

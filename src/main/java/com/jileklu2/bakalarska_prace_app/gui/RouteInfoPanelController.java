package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.wrappers.RouteStepListViewWrapper;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class RouteInfoPanelController implements RouteInfoPanelContext {
    RoutesContext routesContext;

    private ListView<RouteStepListViewWrapper> listView;

    public RouteInfoPanelController(RoutesContext routesContext, ListView<RouteStepListViewWrapper> listView) {
        this.routesContext = routesContext;
        this.listView = listView;
        setDoubleClickEvent();
    }

    @Override
    public void showDefaultRouteInfo() {
        listView.getItems().clear();
        Route defaultRoute = routesContext.getDefaultRoute();
        List<RouteStep> defaultRouteSteps = defaultRoute.getRouteSteps();
        for(RouteStep routeStep : defaultRouteSteps) {
            listView.getItems().add(new RouteStepListViewWrapper(routeStep));
        }
    }

    private void setDoubleClickEvent() {
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    RouteStepListViewWrapper selectedItem = listView.getSelectionModel().getSelectedItem();
                    System.out.println(selectedItem.getRouteStep().getOrigin());
                }
            }
        });
    }

}

package com.jileklu2.bakalarska_prace_app.gui.infoPannelHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;
import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.mapObjects.RouteStep;
import com.jileklu2.bakalarska_prace_app.mapObjects.wrappers.RouteStepListViewWrapper;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;

public class RouteInfoPanel implements RouteInfoPanelContext {
    private final RoutesContext routesContext;

    private final MainContext mainContext;

    private final ListView<RouteStepListViewWrapper> listView;

    public RouteInfoPanel(RoutesContext routesContext,
                          MainContext mainContext,
                          ListView<RouteStepListViewWrapper> listView) {
        if(routesContext == null || mainContext == null || listView == null)
            throw new NullPointerException("Arguments can't be null");

        this.routesContext = routesContext;
        this.mainContext = mainContext;
        this.listView = listView;
        setDoubleClickEvent();
    }

    @Override
    public void setDefaultRouteInfo() throws DefaultRouteNotSetException {
        listView.getItems().clear();
        Route defaultRoute = routesContext.getDefaultRoute();
        List<RouteStep> defaultRouteSteps = defaultRoute.getRouteSteps();
        for(RouteStep routeStep : defaultRouteSteps) {
            listView.getItems().add(new RouteStepListViewWrapper(routeStep));
        }
    }

    private void setDoubleClickEvent() {
        listView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                RouteStepListViewWrapper selectedRouteStep = listView.getSelectionModel().getSelectedItem();
                try {
                    if(selectedRouteStep == null)
                        return;
                    mainContext.openRouteStepEditWindow(selectedRouteStep.getRouteStep());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

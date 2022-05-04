package com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.wrappers;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;

import java.util.Locale;

public class RouteStepListViewWrapper {
    private final RouteStep routeStep;

    public RouteStepListViewWrapper(RouteStep routeStep) {
        this.routeStep = routeStep;
    }

    @Override
    public String toString() {
        return routeStep.toFormattedString();
    }

    public RouteStep getRouteStep() {
        return routeStep;
    }
}

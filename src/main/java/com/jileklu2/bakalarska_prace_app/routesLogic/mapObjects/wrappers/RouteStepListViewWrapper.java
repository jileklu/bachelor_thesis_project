package com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.wrappers;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;


public class RouteStepListViewWrapper {
    private final RouteStep routeStep;

    public RouteStepListViewWrapper(RouteStep routeStep) {
        if(routeStep == null)
            throw new NullPointerException("Arguments can't be null");

        this.routeStep = routeStep;
    }

    @Override
    public String toString() {
        return routeStep.toFormattedString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RouteStepListViewWrapper other = (RouteStepListViewWrapper) obj;

        return routeStep.equals(obj);
    }

    @Override
    public int hashCode() {
        final int prime = 7;
        int result = 1;
        int add = 0;

        if(routeStep != null) {
            add = routeStep.hashCode();
        } else {
            add = 0;
        }

        result = prime * result + add;
        return result;
    }

    public RouteStep getRouteStep() {
        return routeStep;
    }
}

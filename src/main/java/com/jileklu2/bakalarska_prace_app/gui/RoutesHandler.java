package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.mapObjects.Route;


public class RoutesHandler implements RoutesContext {
    private Route defaultRoute;

    public RoutesHandler() {}

    @Override
    public void setDefaultRoute(Route defaultRoute){
        this.defaultRoute = defaultRoute;
    }

    @Override
    public Route getDefaultRoute() {
        return defaultRoute;
    }
}

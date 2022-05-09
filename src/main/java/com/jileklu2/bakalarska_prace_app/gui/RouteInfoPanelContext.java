package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;

public interface RouteInfoPanelContext {
    void showDefaultRouteInfo() throws DefaultRouteNotSetException;
}

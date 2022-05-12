package com.jileklu2.bakalarska_prace_app.gui.infoPannelHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;

public interface RouteInfoPanelContext {
    void setDefaultRouteInfo() throws DefaultRouteNotSetException;
}

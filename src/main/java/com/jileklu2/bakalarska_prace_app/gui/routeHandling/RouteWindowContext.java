package com.jileklu2.bakalarska_prace_app.gui.routeHandling;

import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;

public interface RouteWindowContext {
    void setRoutesContext(RoutesContext routesContext);
    void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext);
    void setMapViewContext(MapViewContext mapViewContext);
}

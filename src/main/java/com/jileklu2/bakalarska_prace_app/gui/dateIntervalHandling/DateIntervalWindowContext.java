package com.jileklu2.bakalarska_prace_app.gui.dateIntervalHandling;

import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;

public interface DateIntervalWindowContext {
    void setRoutesContext(RoutesContext routesContext);

    void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext);

    void setMapViewContext(MapViewContext mapViewContext);
}

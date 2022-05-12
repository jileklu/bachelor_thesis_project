package com.jileklu2.bakalarska_prace_app.gui.routeHandling.dateIntervalHandling;

import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.mapHandling.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.infoPannelHandling.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;

public interface DateIntervalWindowContext {
    void setRoutesContext(RoutesContext routesContext);

    void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext);

    void setMapViewContext(MapViewContext mapViewContext);

    void setMainContext(MainContext mainContext);
}

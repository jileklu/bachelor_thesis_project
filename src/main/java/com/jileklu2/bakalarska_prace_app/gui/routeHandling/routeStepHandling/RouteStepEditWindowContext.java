package com.jileklu2.bakalarska_prace_app.gui.routeHandling.routeStepHandling;

import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.mapHandling.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.infoPannelHandling.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.mapObjects.RouteStep;

public interface RouteStepEditWindowContext {
    void setRoutesContext(RoutesContext routesContext);

    void setMapViewContext(MapViewContext mapViewContext);

    void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext);

    void setRouteStep(RouteStep routeStep);

    void setMainContext(MainContext mainContext);
}

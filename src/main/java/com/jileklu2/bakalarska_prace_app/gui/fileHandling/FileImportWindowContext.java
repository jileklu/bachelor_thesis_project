package com.jileklu2.bakalarska_prace_app.gui.fileHandling;

import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;

public interface FileImportWindowContext {
    void setRoutesContext(RoutesContext routesContext);

    void setMapViewContext(MapViewContext mapViewContext);

    void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext);
}
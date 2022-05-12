package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.mapObjects.RouteStep;

import java.io.IOException;

public interface MainContext {
    void openRouteStepEditWindow(RouteStep routeStep) throws IOException;

    void createErrorWindow(String errorMessage) throws IOException;

    void makeDraggable();
}

package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.GuiApplication;
import com.jileklu2.bakalarska_prace_app.gui.errorHandling.ErrorWindowContext;
import com.jileklu2.bakalarska_prace_app.gui.errorHandling.ErrorWindowController;
import com.jileklu2.bakalarska_prace_app.gui.routeStepHandling.RouteStepEditWindowContext;
import com.jileklu2.bakalarska_prace_app.gui.routeStepHandling.RouteStepEditWindowController;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public interface MainContext {
    void openRouteStepEditWindow(RouteStep routeStep) throws IOException;

    void createErrorWindow(String errorMessage) throws IOException;

    void makeDraggable();
}

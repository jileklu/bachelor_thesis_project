package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.GuiApplication;
import com.jileklu2.bakalarska_prace_app.gui.dateIntervalHandling.DateIntervalWindowContext;
import com.jileklu2.bakalarska_prace_app.gui.dateIntervalHandling.DateIntervalWindowController;
import com.jileklu2.bakalarska_prace_app.gui.fileHandling.FileExportWindowController;
import com.jileklu2.bakalarska_prace_app.gui.fileHandling.FileExportWindowContext;
import com.jileklu2.bakalarska_prace_app.gui.fileHandling.FileImportWindowContext;
import com.jileklu2.bakalarska_prace_app.gui.fileHandling.FileImportWindowController;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.*;
import com.jileklu2.bakalarska_prace_app.gui.routeStepHandling.RouteStepEditWindowContext;
import com.jileklu2.bakalarska_prace_app.gui.routeStepHandling.RouteStepEditWindowController;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.wrappers.RouteStepListViewWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class MainViewController implements Initializable, MainContext {
    @FXML
    private WebView webView;

    @FXML
    private Button routeButton;

    @FXML
    private Button importButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button addWaypointButton;

    @FXML
    private Button dateButton;

    @FXML
    private ListView<RouteStepListViewWrapper> listView;

    private RoutesContext routesContext;

    private MapViewContext mapViewContext;

    private RouteInfoPanelContext routeInfoPanelContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main View Init");
        routesContext = new RoutesHandler();
        mapViewContext = new MapContentController(webView, routesContext);
        routeInfoPanelContext = new RouteInfoPanelController(routesContext, this, listView);
        routesContext.setMapViewContext(mapViewContext);
        routesContext.setRouteInfoPanelContext(routeInfoPanelContext);
        initWebView();
    }

    private void initWebView() {
        mapViewContext.loadMap();
        webView.requestFocus();
        JSObject window = (JSObject) webView.getEngine().executeScript("window");
        window.setMember("routesContext", routesContext);
    }

    @FXML
    private void routeButtonAction() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("gui/popupWindows/route_popup.fxml"));
        Parent root = fxmlLoader.load();
        RouteWindowContext routePopUp = fxmlLoader.<RouteWindowController>getController();
        routePopUp.setRoutesContext(routesContext);
        routePopUp.setMapViewContext(mapViewContext);
        routePopUp.setRouteInfoPanelContext(routeInfoPanelContext);
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void importButtonAction() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("gui/popupWindows/import_route_popup.fxml"));
        Parent root = fxmlLoader.load();
        FileImportWindowContext importFilePopUp = fxmlLoader.<FileImportWindowController>getController();
        importFilePopUp.setRoutesContext(routesContext);
        importFilePopUp.setMapViewContext(mapViewContext);
        importFilePopUp.setRouteInfoPanelContext(routeInfoPanelContext);
        Scene scene = new Scene(root, 450, 100);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void exportButtonAction() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("gui/popupWindows/export_route_popup.fxml"));
        Parent root = fxmlLoader.load();
        FileExportWindowContext exportFilePopUp = fxmlLoader.<FileExportWindowController>getController();
        exportFilePopUp.setRoutesContext(routesContext);
        Scene scene = new Scene(root, 450, 100);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addWaypointButtonAction() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("gui/popupWindows/waypoint_popup.fxml"));
        Parent root = fxmlLoader.load();
        RouteWindowContext waypointPopUp = fxmlLoader.<WaypointWindowController>getController();
        waypointPopUp.setRoutesContext(routesContext);
        waypointPopUp.setMapViewContext(mapViewContext);
        waypointPopUp.setRouteInfoPanelContext(routeInfoPanelContext);
        Scene scene = new Scene(root, 450, 100);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void dateButtonAction() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("gui/popupWindows/date_interval_popup.fxml"));
        Parent root = fxmlLoader.load();
        DateIntervalWindowContext timeIntervalPopUp = fxmlLoader.<DateIntervalWindowController>getController();
        timeIntervalPopUp.setRoutesContext(routesContext);
        timeIntervalPopUp.setMapViewContext(mapViewContext);
        timeIntervalPopUp.setRouteInfoPanelContext(routeInfoPanelContext);
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void openRouteStepEditWindow(RouteStep routeStep) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("gui/popupWindows/route_step_edit_popup.fxml"));
        Parent root = fxmlLoader.load();
        RouteStepEditWindowContext routeStepEditPopUp  = fxmlLoader.<RouteStepEditWindowController>getController();
        routeStepEditPopUp.setRoutesContext(routesContext);
        routeStepEditPopUp.setMapViewContext(mapViewContext);
        routeStepEditPopUp.setRouteInfoPanelContext(routeInfoPanelContext);
        routeStepEditPopUp.setRouteStep(routeStep);
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
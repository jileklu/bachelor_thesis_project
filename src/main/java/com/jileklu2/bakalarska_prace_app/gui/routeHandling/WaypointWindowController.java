package com.jileklu2.bakalarska_prace_app.gui.routeHandling;

import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WaypointWindowController implements RouteWindowContext, Initializable {
    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    @FXML
    private TextField latTextField;

    @FXML
    private TextField lngTextField;

    private String waypointLatStr;

    private String waypointLngStr;

    private RoutesContext routesContext;

    private RouteInfoPanelContext routeInfoPanelContext;

    private MapViewContext mapViewContext;

    @Override
    public void setRoutesContext(RoutesContext routesContext) {
        this.routesContext = routesContext;
    }

    @Override
    public void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext) {
        this.routeInfoPanelContext = routeInfoPanelContext;
    }

    @Override
    public void setMapViewContext(MapViewContext mapViewContext) {
        this.mapViewContext = mapViewContext;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("New Waypoint Popup Init");
    }

    @FXML
    private void okButtonAction() {
        try{
            Double lat = Double.valueOf(waypointLatStr);
            Double lng = Double.valueOf(waypointLngStr);

            Coordinates waypoint = new Coordinates(lat, lng);
            Route newRoute = new Route(routesContext.getDefaultRoute());
            newRoute.addWaypoint(waypoint);
            routesContext.findRouteInfo(newRoute);
            routesContext.setDefaultRoute(newRoute);
            mapViewContext.showDefaultRoute();
            routeInfoPanelContext.showDefaultRouteInfo();
        }catch (Exception e) {
            //todo
            e.printStackTrace();
        }
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void latTextFieldKeyTypedAction() {
        waypointLatStr = latTextField.getText();
    }

    @FXML
    private void lngTextFieldKeyTypedAction() {
        waypointLngStr = lngTextField.getText();
    }
}

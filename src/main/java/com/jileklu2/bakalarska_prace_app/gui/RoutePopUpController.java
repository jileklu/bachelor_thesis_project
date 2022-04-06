package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.RouteStepsFinder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class RoutePopUpController implements RoutePopUpContext, Initializable {

    private RoutesContext routesContext;

    private MapViewContext mapViewContext;

    private RouteInfoPanelContext routeInfoPanelContext;

    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button waypointBtn;

    @FXML
    private TextField orgLatTextField;

    @FXML
    private TextField orgLngTextField;

    @FXML
    private TextField destLatTextField;

    @FXML
    private TextField destLngTextField;

    @FXML
    private GridPane gridPane;

    private String routeOriginLat;
    private String routeDestLat;
    private String routeOriginLng;
    private String routeDestLng;

    private int waypointCount = 0;

    private List<String> waypointsLats;
    private List<String> waypointsLngs;

    private List<TextField> latTextFields;
    private List<TextField> lngTextFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Route Popup Init");
        orgLatTextField.requestFocus();
        latTextFields = new ArrayList<>();
        lngTextFields = new ArrayList<>();
        waypointsLats = new ArrayList<>();
        waypointsLngs = new ArrayList<>();
    }

    @FXML
    private void okBtnAction() {
        if(routeOriginLat == null || routeDestLat == null || routeOriginLng == null || routeDestLng == null)
            //todo
            return;

        Coordinates origin = new Coordinates(Double.parseDouble(routeOriginLat), Double.parseDouble(routeOriginLng));
        Coordinates destination = new Coordinates(Double.parseDouble(routeDestLat), Double.parseDouble(routeDestLng));
        LinkedHashSet<Coordinates> waypoints = new LinkedHashSet<>();
        for( int i = 0; i < waypointCount; i++) {
            if( waypointsLats.get(i) == null || waypointsLngs.get(i) == null)
                //todo
                continue;

            Coordinates waypoint = new Coordinates(Double.parseDouble(waypointsLats.get(i)),
                                                   Double.parseDouble(waypointsLngs.get(i)));
            waypoints.add(waypoint);
        }
        Route newRoute = new Route(origin, destination, waypoints);
        RouteStepsFinder.findRouteSteps(newRoute, true);
        routesContext.setDefaultRoute(newRoute);
        mapViewContext.showDefaultRoute();
        routeInfoPanelContext.showDefaultRouteInfo();
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelBtnAction() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void waypointBtnAction() {
        int row = waypointCount + 2;
        Text text = new Text("Waypoint " + (waypointCount + 1));
        TextField latTextField = new TextField();
        TextField lngTextField = new TextField();

        latTextFields.add(latTextField);
        lngTextFields.add(lngTextField);

        //LatTextField set up
        latTextField.setOnKeyTyped(event -> {
            TextField source = (TextField) event.getSource();
            int index = latTextFields.indexOf(source);
            waypointsLats.set(index, source.getText());
        });
        latTextField.setPromptText("Lat");
        latTextField.setPrefHeight(25);
        latTextField.setPrefWidth(100);
        latTextField.setAlignment(Pos.CENTER_LEFT);

        //LngTextField set up
        lngTextField.setOnKeyTyped(event -> {
            TextField source = (TextField) event.getSource();
            int index = lngTextFields.indexOf(source);
            waypointsLngs.set(index, source.getText());
        });
        lngTextField.setPromptText("Lng");
        lngTextField.setPrefHeight(25);
        lngTextField.setPrefWidth(100);
        lngTextField.setAlignment(Pos.CENTER_LEFT);

        gridPane.addRow(row);
        gridPane.add(text,0,row);
        gridPane.add(latTextField, 1, row);
        gridPane.add(lngTextField, 2, row);

        //Waypoint lat and lng added to refer to in the future
        waypointsLats.add(null);
        waypointsLngs.add(null);

        waypointCount++;
    }

    @FXML
    private void originLatKeyTextFieldTypedAction() {
        routeOriginLat = orgLatTextField.getText();
    }

    @FXML
    private void originLngTextFieldKeyTypedAction() {
        routeOriginLng = orgLngTextField.getText();
    }

    @FXML
    private void destinationLatKeyTextFieldTypedAction() {
        routeDestLat = destLatTextField.getText();
    }

    @FXML
    private void destinationLngKeyTextFieldTypedAction() {
        routeDestLng = destLngTextField.getText();
    }

    public void setRoutesContext(RoutesContext routesContext) {
        this.routesContext = routesContext;
    }

    public void setMapViewContext(MapViewContext mapViewContext) {
        this.mapViewContext = mapViewContext;
    }

    public void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext) {
        this.routeInfoPanelContext = routeInfoPanelContext;
    }
}

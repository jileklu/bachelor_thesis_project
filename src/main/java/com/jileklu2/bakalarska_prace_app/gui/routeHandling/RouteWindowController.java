package com.jileklu2.bakalarska_prace_app.gui.routeHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.BlankScriptNameStringException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.strings.BlankStringException;
import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RouteWindowController implements RouteWindowContext, Initializable {

    private RoutesContext routesContext;

    private MapViewContext mapViewContext;

    private RouteInfoPanelContext routeInfoPanelContext;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addWaypointButton;

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
    private MainContext mainContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("New Route Popup Init");
        orgLatTextField.requestFocus();
        latTextFields = new ArrayList<>();
        lngTextFields = new ArrayList<>();
        waypointsLats = new ArrayList<>();
        waypointsLngs = new ArrayList<>();
    }

    @Override
    public void setRoutesContext(RoutesContext routesContext) {
        if(routesContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.routesContext = routesContext;
    }

    @Override
    public void setMapViewContext(MapViewContext mapViewContext) {
        if(mapViewContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.mapViewContext = mapViewContext;
    }

    @Override
    public void setMainContext(MainContext mainContext) {
        if(mapViewContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.mainContext = mainContext;
    }

    public void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext) {
        if(routeInfoPanelContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.routeInfoPanelContext = routeInfoPanelContext;
    }

    @FXML
    private void okButtonAction() throws IdenticalCoordinatesException, DistanceOutOfBoundsException,
    EmptyTimeStampsSetException,CoordinatesOutOfBoundsException, EmptyDestinationsListException,
    DurationOutOfBoundsException, BlankScriptNameStringException, IOException {
        Coordinates origin;
        Coordinates destination;

        try {
            origin = new Coordinates(Double.parseDouble(routeOriginLat), Double.parseDouble(routeOriginLng));
            destination = new Coordinates(Double.parseDouble(routeDestLat), Double.parseDouble(routeDestLng));
        } catch (NullPointerException e) {
            mainContext.createErrorWindow("Origin and destination coordinates can't be null.");
            System.out.println("Origin and destination coordinates can't be null.");
            e.printStackTrace();
            return;
        } catch (CoordinatesOutOfBoundsException e) {
            mainContext.createErrorWindow("Origin or destination coordinates are out of expected bounds.");
            System.out.println("Origin or destination coordinates are out of expected bounds.");
            e.printStackTrace();
            return;
        } catch (NumberFormatException e) {
            mainContext.createErrorWindow("Coordinates are not in the correct format.");
            System.out.println("Coordinates are not in the correct format.");
            e.printStackTrace();
            return;
        }

        LinkedHashSet<Coordinates> waypoints = new LinkedHashSet<>();

        for( int i = 0; i < waypointCount; i++) {
            Coordinates waypoint;

            try {
                waypoint = new Coordinates(Double.parseDouble(waypointsLats.get(i)),
                                                       Double.parseDouble(waypointsLngs.get(i)));
            } catch (NullPointerException e) {
                mainContext.createErrorWindow("Waypoint" + i + " can't be null");
                System.out.println("Waypoint" + i + "point can't be null");
                e.printStackTrace();
                return;
            } catch (CoordinatesOutOfBoundsException e) {
                mainContext.createErrorWindow("Waypoint" + i + " coordinates are out of expected bounds.");
                System.out.println("Waypoint" + i + " coordinates are out of expected bounds.");
                e.printStackTrace();
                return;
            }  catch (NumberFormatException e) {
                mainContext.createErrorWindow("Waypoint" + i + "coordinates are not in the correct format.");
                System.out.println("Waypoint" + i + "coordinates are not in the correct format.");
                e.printStackTrace();
                return;
            }

            waypoints.add(waypoint);
        }

        Route newRoute;
        try {
            newRoute = new Route(origin, destination, waypoints);
        } catch (IdenticalCoordinatesException e) {
            mainContext.createErrorWindow("Origin and destination can't be the same coordinates.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            routesContext.findRouteInfo(newRoute);
        } catch (InterruptedException e) {
            mainContext.createErrorWindow("Working thread interrupted.");
            System.out.println("Thread error.");
            e.printStackTrace();
            return;
        } catch (RouteLengthExceededException | CreatedException | RequestDeniedException | OverDailyLimitException |
                 OverQueryLimitException | WaypointsNumberExceededException | ZeroResultsException |
                 InvalidRequestException | DataNotAvailableException | LocationNotFoundException |
                 UnknownStatusException e) {
            mainContext.createErrorWindow(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        routesContext.setDefaultRoute(newRoute);

        try {
            mapViewContext.showDefaultRoute();
            routeInfoPanelContext.showDefaultRouteInfo();
        } catch (DefaultRouteNotSetException e) {
            mainContext.createErrorWindow("Route has to be created before showing.");
            System.out.println("Route has to be created before showing.");
            e.printStackTrace();
            return;
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
    private void addWaypointButtonAction() {
        int row = waypointCount + 2;
        Text text = new Text("Waypoint " + (waypointCount + 1));
        text.setFill(Color.WHITE);
        TextField latTextField = new TextField();
        TextField lngTextField = new TextField();

        latTextFields.add(latTextField);
        lngTextFields.add(lngTextField);

        latTextFieldSetUp(latTextField);
        lngTextFieldSetUp(lngTextField);

        addGridPaneRow(row, text, latTextField, lngTextField);

        //Waypoint lat and lng objects added for future referencing
        waypointsLats.add(null);
        waypointsLngs.add(null);

        waypointCount++;
    }

    private void latTextFieldSetUp(TextField textField) {
        textField.setOnKeyTyped(event -> {
            TextField source = (TextField) event.getSource();
            int index = latTextFields.indexOf(source);
            waypointsLats.set(index, source.getText());
        });

        textFieldPositionSetUp(textField, "Lat");
    }

    private void lngTextFieldSetUp(TextField textField) {
        textField.setOnKeyTyped(event -> {
            TextField source = (TextField) event.getSource();
            int index = lngTextFields.indexOf(source);
            waypointsLngs.set(index, source.getText());
        });

        textFieldPositionSetUp(textField, "Lng");
    }

    private void textFieldPositionSetUp(TextField textField, String promptText) {
        textField.setPromptText(promptText);
        textField.setPrefHeight(25);
        textField.setPrefWidth(100);
        textField.setAlignment(Pos.CENTER_LEFT);
    }

    private void addGridPaneRow(int row, Text text,TextField textFieldL,TextField textFieldR) {
        gridPane.addRow(row);
        gridPane.add(text,0,row);
        gridPane.add(textFieldL, 1, row);
        gridPane.add(textFieldR, 2, row);
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
}

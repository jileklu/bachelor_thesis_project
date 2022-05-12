package com.jileklu2.bakalarska_prace_app.gui.routeHandling.waypointHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.BlankScriptNameStringException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.mapHandling.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.infoPannelHandling.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WaypointWindowController implements WaypointViewContext, Initializable {
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
    private MainContext mainContext;

    @Override
    public void setRoutesContext(RoutesContext routesContext) {
        if(routesContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.routesContext = routesContext;
    }

    @Override
    public void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext) {
        if(routeInfoPanelContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.routeInfoPanelContext = routeInfoPanelContext;
    }

    @Override
    public void setMapViewContext(MapViewContext mapViewContext) {
        if(mapViewContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.mapViewContext = mapViewContext;
    }

    @Override
    public void setMainContext(MainContext mainContext) {
        if(mainContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.mainContext = mainContext;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("New Waypoint Popup Init");
    }

    @FXML
    private void okButtonAction() throws IdenticalCoordinatesException, DistanceOutOfBoundsException,
    EmptyTimeStampsSetException, CoordinatesOutOfBoundsException, EmptyDestinationsListException,
    DurationOutOfBoundsException, BlankScriptNameStringException, IOException {
        double lat;
        double lng;

        try {
            lat = Double.parseDouble(waypointLatStr);
            lng = Double.parseDouble(waypointLngStr);
        }  catch (NumberFormatException e) {
            mainContext.createErrorWindow("Waypoint coordinates are not in the correct format.");
            System.out.println("Coordinates are not in the correct format.");
            e.printStackTrace();
            return;
        } catch (NullPointerException e) {
            mainContext.createErrorWindow("Waypoint coordinates can't be null.");
            System.out.println("Waypoint coordinates can't be null.");
            e.printStackTrace();
            return;
        }


        Coordinates waypoint;
        try {
            waypoint = new Coordinates(lat, lng);
        } catch (CoordinatesOutOfBoundsException e) {
            mainContext.createErrorWindow("Waypoint coordinates are out of expected bounds.");
            System.out.println("Waypoint coordinates are out of expected bounds.");
            e.printStackTrace();
            return;
        }

        Route newRoute = null;
        try {
            newRoute = new Route(routesContext.getDefaultRoute());
        } catch (DefaultRouteNotSetException e) {
            mainContext.createErrorWindow("Route has to be created before adding waypoints.");
            System.out.println("Route has to be created before adding waypoints.");
            e.printStackTrace();
            return;
        }
        newRoute.addWaypoint(waypoint);
        try {
            routesContext.findRouteInfo(newRoute);
        } catch (InterruptedException e) {
            mainContext.createErrorWindow("Working thread interrupted.");
            System.out.println("Thread error.");
            e.printStackTrace();
            return;
        } catch (RouteLengthExceededException | CreatedException | UnknownStatusException | LocationNotFoundException |
                 DataNotAvailableException | InvalidRequestException | ZeroResultsException |
                 WaypointsNumberExceededException | OverQueryLimitException | OverDailyLimitException |
                 RequestDeniedException e) {
            mainContext.createErrorWindow(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }
        routesContext.setDefaultRoute(newRoute);

        try {
            mapViewContext.showDefaultRoute();
            routeInfoPanelContext.setDefaultRouteInfo();
        } catch (DefaultRouteNotSetException e) {
            mainContext.createErrorWindow("Route has to be created before adding waypoints.");
            System.out.println("Route has to be created before adding waypoints.");
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
    private void latTextFieldKeyTypedAction() {
        waypointLatStr = latTextField.getText();
    }

    @FXML
    private void lngTextFieldKeyTypedAction() {
        waypointLngStr = lngTextField.getText();
    }
}

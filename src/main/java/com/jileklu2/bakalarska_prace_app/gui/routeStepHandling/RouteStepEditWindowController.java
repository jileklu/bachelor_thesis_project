package com.jileklu2.bakalarska_prace_app.gui.routeStepHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.BlankScriptNameStringException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.AverageSpeedOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.strings.BlankStringException;
import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Variable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class RouteStepEditWindowController implements Initializable, RouteStepEditWindowContext {

    @FXML
    private TextField orgLatTextField;

    @FXML
    private TextField destLatTextField;

    @FXML
    private TextField orgLngTextField;

    @FXML
    private TextField destLngTextField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addVariableButton;

    @FXML
    private GridPane gridPane;

    private RoutesContext routesContext;
    private MapViewContext mapViewContext;
    private RouteInfoPanelContext routeInfoPanelContext;
    private MainContext mainContext;
    private RouteStep routeStep;
    private List<TextField> variableNamesTextFields;
    private List<TextField> variableValuesTextFields;
    private List<String> variableNames;
    private List<String> variableValues;
    private int variablesCount = 0;
    private boolean coordinatesChanged = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        variableNamesTextFields = new ArrayList<>();
        variableValuesTextFields = new ArrayList<>();
        variableNames = new ArrayList<>();
        variableValues = new ArrayList<>();
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
    public void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext) {
        if(routeInfoPanelContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.routeInfoPanelContext = routeInfoPanelContext;
    }

    @Override
    public void setRouteStep(RouteStep routeStep) {
        if(routeStep == null)
            throw new NullPointerException("Arguments can't be null");

        this.routeStep = routeStep;
        initTextFields();
    }

    @Override
    public void setMainContext(MainContext mainContext) {
        if(mainContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.mainContext = mainContext;
    }

    private void initTextFields() {
        orgLatTextField.setText(routeStep.getOrigin().getLat().toString());
        destLatTextField.setText(routeStep.getDestination().getLat().toString());
        orgLngTextField.setText(routeStep.getOrigin().getLng().toString());
        destLngTextField.setText(routeStep.getDestination().getLng().toString());

        for(Variable variable : routeStep.getVariables()) {
            addVariableRow();
            variableNames.set(variableNames.size() - 1, variable.getName());
            variableValues.set(variableValues.size() - 1, variable.getValue());
            variableNamesTextFields.get(variableNamesTextFields.size() - 1).setText(variable.getName());
            variableValuesTextFields.get(variableValuesTextFields.size() - 1).setText(variable.getValue());
        }

        initTypedEvent(orgLatTextField);
        initTypedEvent(destLatTextField);
        initTypedEvent(orgLngTextField);
        initTypedEvent(destLngTextField);
    }

    private void initTypedEvent(TextField textField) {
        textField.setOnKeyTyped(event -> coordinatesChanged = true);
    }

    @FXML
    private void okButtonAction() throws DistanceOutOfBoundsException, AverageSpeedOutOfBoundsException,
    EmptyTimeStampsSetException, EmptyDestinationsListException, DurationOutOfBoundsException,
    IdenticalCoordinatesException, BlankScriptNameStringException, IOException {
        if (coordinatesChanged) {
            try {
                routesContext.changeRouteCoordinates(routeStep, getCoordinates());
            } catch (CoordinatesOutOfBoundsException e) {
                mainContext.createErrorWindow("Coordinates are out of expected bounds.");
                System.out.println("Coordinates are out of expected bounds.");
                e.printStackTrace();
                return;
            } catch (DefaultRouteNotSetException e) {
                mainContext.createErrorWindow("Route has to be created before changing its steps.");
                System.out.println("Route has to be created before changing its steps.");
                e.printStackTrace();
                return;
            } catch (NullPointerException e) {
                mainContext.createErrorWindow("Coordinates can't be null.");
                System.out.println("Coordinates can't be null.");
                e.printStackTrace();
                return;
            }

            try {
                routesContext.findRouteStepInfo(routeStep);
            } catch (IdenticalCoordinatesException e) {
                mainContext.createErrorWindow("Origin and destination can't be the same coordinates.");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return;
            } catch (CoordinatesOutOfBoundsException e) {
                mainContext.createErrorWindow("Coordinates are out of expected bounds.");
                System.out.println("Coordinates are out of expected bounds.");
                e.printStackTrace();
                return;
            } catch (DefaultRouteNotSetException e) {
                mainContext.createErrorWindow("Route has to be created before changing its steps.");
                System.out.println("Route has to be created before changing its steps.");
                e.printStackTrace();
                return;
            }
            try {
                mapViewContext.showDefaultRoute();
            } catch (DefaultRouteNotSetException e) {
                mainContext.createErrorWindow("Route has to be created before changing its steps.");
                System.out.println("Route has to be created before changing its steps.");
                e.printStackTrace();
                return;
            }
        }
        try {
            changeVariables();
        } catch (BlankStringException e) {
            mainContext.createErrorWindow("Variable can't be a blank string.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            routeInfoPanelContext.showDefaultRouteInfo();
        } catch (DefaultRouteNotSetException e) {
            mainContext.createErrorWindow("Route has to be created before changing its steps.");
            System.out.println("Route has to be created before changing its steps.");
            e.printStackTrace();
            return;
        }

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void changeVariables() throws BlankStringException {
        HashSet<Variable> newVariables = new HashSet<>();

        for(int i = 0; i < variableNames.size(); i++) {
            String name = variableNames.get(i);
            String value = variableValues.get(i);

            newVariables.add(new Variable(name, value));
        }

        routeStep.setVariables(newVariables);
    }

    private Pair<Coordinates,Coordinates> getCoordinates() throws CoordinatesOutOfBoundsException {
        Double orgLat = Double.valueOf(orgLatTextField.getText());
        Double orgLng = Double.valueOf(orgLngTextField.getText());
        Double destLat = Double.valueOf(destLatTextField.getText());
        Double destLng = Double.valueOf(destLngTextField.getText());

        return new Pair<>(new Coordinates(orgLat, orgLng), new Coordinates(destLat, destLng));
    }

    @FXML
    private void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void addVariableRow() {
        int row = variablesCount + 2;

        Text text = new Text("Variable " + (variablesCount + 1));
        text.setFill(Color.WHITE);
        TextField variableNameTextField = new TextField();
        TextField variableValueTextField = new TextField();

        variableNamesTextFields.add(variableNameTextField);
        variableValuesTextFields.add(variableValueTextField);

        variableNameTextField.setOnKeyTyped(event -> {
            TextField source = (TextField) event.getSource();
            int index = variableNamesTextFields.indexOf(source);
            variableNames.set(index, source.getText());
        });

        textFieldSetUp(variableNameTextField, "Variable Name");

        variableValueTextField.setOnKeyTyped(event -> {
            TextField source = (TextField) event.getSource();
            int index = variableValuesTextFields.indexOf(source);
            variableValues.set(index, source.getText());
        });

        textFieldSetUp(variableValueTextField, "Variable Value");

        addGridPaneRow(row, text, variableNameTextField, variableValueTextField);

        variableNames.add(null);
        variableValues.add(null);

        variablesCount++;
    }

    @FXML
    private void addVariableButtonAction() {
        addVariableRow();
    }

    private void textFieldSetUp(TextField textField, String promptText) {
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
}

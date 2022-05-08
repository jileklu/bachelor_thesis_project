package com.jileklu2.bakalarska_prace_app.gui.routeStepHandling;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

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
        this.routesContext = routesContext;
    }

    @Override
    public void setMapViewContext(MapViewContext mapViewContext) {
        this.mapViewContext = mapViewContext;
    }

    @Override
    public void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext) {
        this.routeInfoPanelContext = routeInfoPanelContext;
    }

    @Override
    public void setRouteStep(RouteStep routeStep) {
        this.routeStep = routeStep;
        initTextFields();
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
    private void okButtonAction() {
        if (coordinatesChanged) {
            routesContext.changeRouteCoordinates(routeStep, getCoordinates());
            routesContext.findRouteStepInfo(routeStep);
            mapViewContext.showDefaultRoute();
        }
        changeVariables();

        routeInfoPanelContext.showDefaultRouteInfo();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void changeVariables() {
        HashSet<Variable> newVariables = new HashSet<>();

        for(int i = 0; i < variableNames.size(); i++) {
            String name = variableNames.get(i);
            String value = variableValues.get(i);

            newVariables.add(new Variable(name, value));
        }

        routeStep.setVariables(newVariables);
    }

    private Pair<Coordinates,Coordinates> getCoordinates() {
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

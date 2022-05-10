package com.jileklu2.bakalarska_prace_app.gui.fileHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.BlankScriptNameStringException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.files.IllegalFilePathFormatException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.route.IdenticalCoordinatesException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileImportWindowController implements FileImportWindowContext, Initializable {
    @FXML
    private TextField textField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    private String filePath;
    private RoutesContext routesContext;
    private MapViewContext mapViewContext;
    private RouteInfoPanelContext routeInfoPanelContext;
    private MainContext mainContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Route Import Popup Init");
        textFieldSetUp(textField);
    }

    private void textFieldSetUp(TextField textField) {
        textField.setOnKeyTyped(event -> {
            TextField source = (TextField) event.getSource();
            filePath = source.getText();
        });
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
    public void setMainContext(MainContext mainContext) {
        if(mainContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.mainContext = mainContext;
    }

    @FXML
    public void okButtonAction() throws EmptyTimeStampsSetException, EmptyDestinationsListException,
        BlankScriptNameStringException, IOException {
        JSONObject routeJson;
        try {
            String path = filePath;

            if(path.length() < 5) {
                throw new IllegalFilePathFormatException("Please enter the path in the correct format.");
            }

            routesContext.loadJsonRoute(path);
            mapViewContext.showDefaultRoute();
            routeInfoPanelContext.showDefaultRouteInfo();

            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        } catch(NumberFormatException e) {
            mainContext.createErrorWindow("Coordinates are not in the correct format.");
            System.out.println("Coordinates are not in the correct format.");
        } catch (NullPointerException | IllegalFilePathFormatException e) {
            mainContext.createErrorWindow("Please enter the path in the correct format.");
            System.out.println("Please enter the path in the correct format.");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            mainContext.createErrorWindow("Please enter existing path.");
            System.out.println("Please enter existing path.");
            e.printStackTrace();
        } catch (IdenticalCoordinatesException e) {
            mainContext.createErrorWindow("Origin and destination can't be the same coordinates.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (DistanceOutOfBoundsException e) {
            mainContext.createErrorWindow("Distance can't be negative.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (CoordinatesOutOfBoundsException e) {
            mainContext.createErrorWindow("Some of the coordinates are out of their expected bounds.");
            System.out.println("Some of the coordinates are out of their expected bounds.");
            e.printStackTrace();
        } catch (DurationOutOfBoundsException e) {
            mainContext.createErrorWindow("Duration can't be negative.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            mainContext.createErrorWindow("Wrong JSON file structure.");
            System.out.println("Wrong JSON file structure.");
            e.printStackTrace();
        } catch (DefaultRouteNotSetException e) {
            mainContext.createErrorWindow("Route has to be created before showing.");
            System.out.println("Route has to be created before showing.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            mainContext.createErrorWindow("Working thread interrupted.");
            System.out.println("Thread error.");
            e.printStackTrace();
        } catch (RouteLengthExceededException | UnknownStatusException | LocationNotFoundException |
                 DataNotAvailableException | InvalidRequestException | ZeroResultsException |
                 WaypointsNumberExceededException | OverQueryLimitException | OverDailyLimitException |
                 RequestDeniedException | CreatedException e) {
            mainContext.createErrorWindow(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

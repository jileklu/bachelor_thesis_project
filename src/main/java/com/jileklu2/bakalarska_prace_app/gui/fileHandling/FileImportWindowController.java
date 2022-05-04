package com.jileklu2.bakalarska_prace_app.gui.fileHandling;

import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.FileNotFoundException;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Route Import Popup Init");

        textField.setOnKeyTyped(event -> {
            TextField source = (TextField) event.getSource();
            filePath = source.getText();
        });
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

    @FXML
    public void okButtonAction() {
        JSONObject routeJson;
        try {
            String path = filePath;

            if(path.length() < 5) {
                throw new IllegalArgumentException("Please enter the path in the correct format.");
            }

            routesContext.loadJsonRoute(path);
            mapViewContext.showDefaultRoute();
            routeInfoPanelContext.showDefaultRouteInfo();

            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Please enter the path or file in the correct format.");
        } catch (FileNotFoundException e) {
            System.out.println("Please enter existing path");
        }

    }

    @FXML
    public void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

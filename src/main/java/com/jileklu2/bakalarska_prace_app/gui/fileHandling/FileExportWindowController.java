package com.jileklu2.bakalarska_prace_app.gui.fileHandling;

import com.jileklu2.bakalarska_prace_app.builders.GpxBuilder;
import com.jileklu2.bakalarska_prace_app.cli.route.RouteHandlerCli;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.jileklu2.bakalarska_prace_app.cli.arguments.ArgumentType.OUT_FILE;

public class FileExportWindowController implements FileExportWindowContext, Initializable {
    @FXML
    TextField textField;

    @FXML
    Button okButton;

    @FXML
    Button cancelButton;

    String filePath;
    private RoutesContext routesContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Route Export Popup Init");

        textField.setOnKeyTyped(event -> {
            TextField source = (TextField) event.getSource();
            filePath = source.getText();
        });
    }

    public void setRoutesContext(RoutesContext routesContext) {
        this.routesContext = routesContext;
    }

    @FXML
    private void okButtonAction() {
        try {
            String path = filePath;

            if(path.length() < 5) {
                throw new IllegalArgumentException("Please enter the path in the correct format.");
            }

            FileHandler.createJsonFile(
                String.valueOf(path),
                routesContext.getDefaultRoute().toJSON()
            );
/*
            FileHandler.createGpxFile(
                String.valueOf(path),
                GpxBuilder.buildRouteGpx(routesContext.getDefaultRoute())
            );
*/
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Please enter the path or file in the correct format.");
        }
    }

    @FXML
    private void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

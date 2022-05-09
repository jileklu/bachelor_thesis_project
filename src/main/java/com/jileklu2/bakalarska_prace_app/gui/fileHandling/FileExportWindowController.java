package com.jileklu2.bakalarska_prace_app.gui.fileHandling;

import com.jileklu2.bakalarska_prace_app.exceptions.routes.routesContext.DefaultRouteNotSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.files.IllegalFilePathFormatException;
import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;
import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileExportWindowController implements FileExportWindowContext, Initializable {
    @FXML
    TextField textField;

    @FXML
    Button okButton;

    @FXML
    Button cancelButton;

    private String filePath;
    private RoutesContext routesContext;
    private MainContext mainContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Route Export Popup Init");

        textField.setOnKeyTyped(event -> {
            TextField source = (TextField) event.getSource();
            filePath = source.getText();
        });
    }

    public void setRoutesContext(RoutesContext routesContext) {
        if(routesContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.routesContext = routesContext;
    }

    @Override
    public void setMainContext(MainContext mainContext) {
        if(mainContext == null)
            throw new NullPointerException("Arguments can't be null");

        this.mainContext = mainContext;
    }

    @FXML
    private void okButtonAction() throws IOException {
        try {
            String path = filePath;

            if(path.length() < 5) {
                throw new IllegalFilePathFormatException("Please enter the path in the correct format.");
            }

            FileHandler.createJsonFile(
                path,
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
        }catch (NullPointerException | IllegalFilePathFormatException e) {
            mainContext.createErrorWindow("Please enter the path in the correct format.");
            System.out.println("Please enter the path in the correct format.");
            e.printStackTrace();
        } catch (DefaultRouteNotSetException e) {
            mainContext.createErrorWindow("Route has to be created before creating route file.");
            System.out.println("Route has to be created before creating file.");
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

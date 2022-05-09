package com.jileklu2.bakalarska_prace_app.gui.errorHandling;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorWindowController implements ErrorWindowContext, Initializable {

    @FXML
    Text textField;

    @FXML
    Button okButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void okButtonAction() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setMessage(String message) {
        textField.setText(message);
    }
}

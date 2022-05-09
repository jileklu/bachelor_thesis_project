package com.jileklu2.bakalarska_prace_app;

import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.MainViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;



public class GuiApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("gui/main_view.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = fxmlLoader.load();
        MainContext mainView = fxmlLoader.<MainViewController>getController();
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("Route App");
        stage.setScene(scene);
        mainView.makeDraggable();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
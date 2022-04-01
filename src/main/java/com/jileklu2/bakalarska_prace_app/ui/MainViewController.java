package com.jileklu2.bakalarska_prace_app.ui;

import com.jileklu2.bakalarska_prace_app.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private WebView webView;

    @FXML
    private Button routeButton;

    @FXML
    private ListView<String> listView;

    private RoutesContext routesContext;

    private MapViewContext mapViewContext;

    private RouteInfoPanelContext routeInfoPanelContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main View Init");
        routesContext = new RoutesHandler();
        mapViewContext = new MapContentHandler(webView, routesContext);
        routeInfoPanelContext = new RouteInfoPanelController(routesContext, listView);
        mapViewContext.loadMap();
        webView.requestFocus();
    }

    @FXML
    private void routeButtonAction() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ui/route_popup.fxml"));
        Parent popUpRoot = fxmlLoader.load();
        RoutePopUpController routesPopUpController = fxmlLoader.getController();
        routesPopUpController.setRoutesContext(routesContext);
        routesPopUpController.setMapViewContext(mapViewContext);
        routesPopUpController.setRouteInfoPanelContext(routeInfoPanelContext);
        Scene scene = new Scene(popUpRoot, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
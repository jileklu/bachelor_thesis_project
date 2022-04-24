package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.cli.arguments.ArgumentsHandlerCli;
import com.jileklu2.bakalarska_prace_app.cli.route.RouteHandlerCli;
import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import org.json.JSONObject;

import java.io.FileNotFoundException;

import static com.jileklu2.bakalarska_prace_app.cli.arguments.ArgumentType.*;

public class ControllerCli {
    ArgumentsHandlerCli argumentsHandler;
    ConsoleMainMenu mainMenu;
    RouteImportConsoleScreen routeImportScreen;
    MakeRouteConsoleScreen makeRouteScreen;

    RouteExportConsoleScreen routeExportScreen;


    public ControllerCli(String[] args) {
        this.argumentsHandler = new ArgumentsHandlerCli(args);
        this.mainMenu = new ConsoleMainMenu(this);
        this.makeRouteScreen = new MakeRouteConsoleScreen(this);
        this.routeImportScreen = new RouteImportConsoleScreen(this);
        this.routeExportScreen = new RouteExportConsoleScreen();
    }

    public void start() {

        if(this.argumentsHandler.getArgValues().containsKey(IN_FILE)) {
            try{
                JSONObject routeJson = FileHandler.readJsonFile(
                    String.valueOf(this.argumentsHandler.getArgValues().get(IN_FILE))
                );

                RouteHandlerCli.setCurrentRoute(routeJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(this.argumentsHandler.getArgValues().containsKey(MENU_ON)) {
            mainMenu.showMenu();
        }

        if(this.argumentsHandler.getArgValues().containsKey(OUT_FILE)) {
            FileHandler.createJsonFile(
                String.valueOf(this.argumentsHandler.getArgValues().get(OUT_FILE)),
                RouteHandlerCli.getCurrentRoute().toJSON()
            );
        }
   }

   public void showMakeRouteScreen() {
       makeRouteScreen.getRoute();
   }

   public void setCurrentRoute(Route route) {
        RouteHandlerCli.setCurrentRoute(route.toJSON());
   }

    public void setCurrentRoute(JSONObject route) {
        RouteHandlerCli.setCurrentRoute(route);
    }

   public void showMakeRouteImportScreen() {
        routeImportScreen.getRoute();
   }

   public void showMakeRouteExportScreen() {
        routeExportScreen.exportRoute();
   }
}

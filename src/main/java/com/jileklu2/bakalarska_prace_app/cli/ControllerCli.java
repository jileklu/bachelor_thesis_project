package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.cli.arguments.ArgumentsHandlerCli;
import com.jileklu2.bakalarska_prace_app.cli.route.RouteHandlerCli;
import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Scanner;

import static com.jileklu2.bakalarska_prace_app.cli.arguments.ArgumentType.*;

public class ControllerCli {
    private ArgumentsHandlerCli argumentsHandler;
    private ConsoleMainMenu mainMenu;
    private RouteImportConsoleScreen routeImportScreen;
    private MakeRouteConsoleScreen makeRouteScreen;
    private RouteExportConsoleScreen routeExportScreen;
    private Scanner scanner;


    public ControllerCli(String[] args) {
        this.argumentsHandler = new ArgumentsHandlerCli(args);
        this.mainMenu = new ConsoleMainMenu(this);
        this.makeRouteScreen = new MakeRouteConsoleScreen(this);
        this.routeImportScreen = new RouteImportConsoleScreen(this);
        this.routeExportScreen = new RouteExportConsoleScreen(this);
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
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

   public Scanner getScanner(){return scanner;}

    public void resetScanner(){scanner=new Scanner(System.in).useLocale(Locale.US);}

    public void stop(){scanner.close();}
}

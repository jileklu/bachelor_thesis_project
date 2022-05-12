package com.jileklu2.bakalarska_prace_app.cli.routeHandling;

public class MakeRouteScreenCliView {

    /**
     * Show waypoint menu
     */
    public void showWaypointsGetterText() {
        System.out.println("Do you want to add route waypoint?");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
    }

    /**
     * Shows message on the console screen
     *
     * @param message Message to be shown
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}

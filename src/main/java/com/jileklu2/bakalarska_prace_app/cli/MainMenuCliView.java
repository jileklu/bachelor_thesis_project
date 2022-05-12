package com.jileklu2.bakalarska_prace_app.cli;

public class MainMenuCliView {

    /**
     * Shows message on the console screen
     *
     * @param message Message to be shown
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Shows main menu on the console screen
     *
     */
    public void showMenuText() {
        System.out.println("Select Action:");
        System.out.println("[1] Make Route");
        System.out.println("[2] Print Route JSON");
        System.out.println("[3] Import Route");
        System.out.println("[4] Export Route");
        System.out.println("[0] Exit");
    }
}

package com.jileklu2.bakalarska_prace_app;

import com.jileklu2.bakalarska_prace_app.cli.ControllerCli;


public class CliApplication {


    public static void main(String[] args) {
        ControllerCli controller = new ControllerCli(args);
        controller.start();
    }
}

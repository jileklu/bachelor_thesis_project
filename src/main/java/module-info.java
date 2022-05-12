module com.example.bakalarska_prace_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.fontawesome5;
    requires java.net.http;

    opens com.jileklu2.bakalarska_prace_app to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app;
    exports com.jileklu2.bakalarska_prace_app.gui;
    opens com.jileklu2.bakalarska_prace_app.gui to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.cli;
    opens com.jileklu2.bakalarska_prace_app.cli to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.cli.routeHandling;
    opens com.jileklu2.bakalarska_prace_app.cli.routeHandling to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.gui.routeHandling;
    opens com.jileklu2.bakalarska_prace_app.gui.routeHandling to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.gui.fileHandling;
    opens com.jileklu2.bakalarska_prace_app.gui.fileHandling to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.gui.errorHandling;
    opens com.jileklu2.bakalarska_prace_app.gui.errorHandling to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.gui.routeHandling.dateIntervalHandling;
    opens com.jileklu2.bakalarska_prace_app.gui.routeHandling.dateIntervalHandling to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.gui.routeHandling.routeStepHandling;
    opens com.jileklu2.bakalarska_prace_app.gui.routeHandling.routeStepHandling to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google;
    opens com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.handlers;
    opens com.jileklu2.bakalarska_prace_app.handlers to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.handlers.responseStatus.here;
    opens com.jileklu2.bakalarska_prace_app.handlers.responseStatus.here to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.gui.mapHandling;
    opens com.jileklu2.bakalarska_prace_app.gui.mapHandling to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.gui.infoPannelHandling;
    opens com.jileklu2.bakalarska_prace_app.gui.infoPannelHandling to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.cli.fileHandling;
    opens com.jileklu2.bakalarska_prace_app.cli.fileHandling to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.handlers.arguments;
    opens com.jileklu2.bakalarska_prace_app.handlers.arguments to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.gui.routeHandling.waypointHandling;
    opens com.jileklu2.bakalarska_prace_app.gui.routeHandling.waypointHandling to javafx.fxml;

    requires json;
    requires com.fasterxml.jackson.databind;
    requires jdk.jsobject;
}
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
    exports com.jileklu2.bakalarska_prace_app.cli.arguments;
    opens com.jileklu2.bakalarska_prace_app.cli.arguments to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app.cli.route;
    opens com.jileklu2.bakalarska_prace_app.cli.route to javafx.fxml;

    requires json;
    requires com.fasterxml.jackson.databind;
}
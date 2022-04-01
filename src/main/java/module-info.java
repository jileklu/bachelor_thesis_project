module com.example.bakalarska_prace_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.net.http;

    opens com.jileklu2.bakalarska_prace_app to javafx.fxml;
    exports com.jileklu2.bakalarska_prace_app;
    exports com.jileklu2.bakalarska_prace_app.ui;
    opens com.jileklu2.bakalarska_prace_app.ui to javafx.fxml;

    requires json;
}
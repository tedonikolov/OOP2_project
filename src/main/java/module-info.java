module bg.tu_varna.sit.oop2_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens bg.tu_varna.sit.oop2_project to javafx.fxml;
    exports bg.tu_varna.sit.oop2_project;
}
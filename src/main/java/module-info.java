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
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    opens bg.tu_varna.sit.oop2_project to javafx.fxml;
    exports bg.tu_varna.sit.oop2_project;
    exports bg.tu_varna.sit.oop2_project.entities;
    opens bg.tu_varna.sit.oop2_project.entities to javafx.fxml;
    exports bg.tu_varna.sit.oop2_project.controllers;
    opens bg.tu_varna.sit.oop2_project.controllers to javafx.fxml;
    exports bg.tu_varna.sit.oop2_project.backend.DTO;
    opens bg.tu_varna.sit.oop2_project.backend.DTO to javafx.fxml;
    exports bg.tu_varna.sit.oop2_project.backend;
    opens bg.tu_varna.sit.oop2_project.backend to javafx.fxml;
}
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
    exports bg.tu_varna.sit.oop2_project.dataLayer.entities;
    opens bg.tu_varna.sit.oop2_project.dataLayer.entities to javafx.fxml;
    exports bg.tu_varna.sit.oop2_project.presentationLayer.controllers;
    opens bg.tu_varna.sit.oop2_project.presentationLayer.controllers to javafx.fxml;
    exports bg.tu_varna.sit.oop2_project.dataLayer.DTO;
    opens bg.tu_varna.sit.oop2_project.dataLayer.DTO to javafx.fxml;
    exports bg.tu_varna.sit.oop2_project.busnessLayer;
    opens bg.tu_varna.sit.oop2_project.busnessLayer to javafx.fxml;
    exports bg.tu_varna.sit.oop2_project.dataLayer;
    opens bg.tu_varna.sit.oop2_project.dataLayer to javafx.fxml;
}
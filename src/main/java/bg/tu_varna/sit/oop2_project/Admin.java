package bg.tu_varna.sit.oop2_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public class Admin {
    @FXML
    private AnchorPane stage;
    public void createProfile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("registration.fxml"));
        Stage stage1 = (Stage) stage.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage1.setScene(scene);
        stage1.show();
    }
}

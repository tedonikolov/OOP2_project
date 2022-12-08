package bg.tu_varna.sit.oop2_project.backend;

import bg.tu_varna.sit.oop2_project.EventOrganizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class SceneChanger {
    public static void change(ActionEvent event, String fxml){
        try {
            Stage stage;
            Scene scene;
            FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource(fxml));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }

    }
}

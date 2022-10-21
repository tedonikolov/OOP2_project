package bg.tu_varna.sit.oop2_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class EventOrganizer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image("/logo.png");
        stage.getIcons().add(icon);
        stage.setTitle("EventOrganizer");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        Connection connection= Database.connection();
        launch();
        connection.close();
    }
}
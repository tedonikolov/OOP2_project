package bg.tu_varna.sit.oop2_project.controllers;

import bg.tu_varna.sit.oop2_project.backend.Database;
import bg.tu_varna.sit.oop2_project.EventOrganizer;
import bg.tu_varna.sit.oop2_project.backend.Profile;
import bg.tu_varna.sit.oop2_project.entities.Event;
import bg.tu_varna.sit.oop2_project.entities.Organiser;
import bg.tu_varna.sit.oop2_project.backend.collections.GetOrganisers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static eu.hansolo.tilesfx.Tile.GREEN;

public class EventController {
    private Stage stage;
    private Scene scene;
    private Organiser organiser;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private DatePicker date;
    @FXML
    private TextField time;
    @FXML
    private TextArea description;
    @FXML
    private Label error;
    @FXML
    private Label complete;

    public void create() throws SQLException {
        if(!Objects.equals(name.getText(), "")&& !Objects.equals(address.getText(), "")&& !Objects.equals(time.getText(), "")&& !Objects.equals(description.getText(), "")) {
            if (time.getText().contains(":")) {
                Connection connection = Database.connection();
                Statement statement = connection.createStatement();
                String getId = "SELECT EVENT_SEQUENCE.nextVal from DUAL";
                ResultSet rs = statement.executeQuery(getId);
                int id = 0;
                if (rs.next())
                    id = rs.getInt(1);

                String[] arr = time.getText().split(":");
                int hour = Integer.parseInt(arr[0]);
                int minute = Integer.parseInt(arr[1]);
                LocalDateTime dateTime = LocalDateTime.of(date.getValue().getYear(), date.getValue().getMonth(), date.getValue().getDayOfMonth(), hour, minute);

                for (Organiser organiser : GetOrganisers.get())
                    if (organiser.getIdProfile() == Profile.getProfiles().getIdProfile())
                        this.organiser = organiser;

                Event event = new Event(id, name.getText(), address.getText(), dateTime, description.getText(), organiser);

                String sql = "INSERT INTO EVENT(ID_EVENT,NAME,ADDRESS,DATETIME,DESCRIPTION,ORGANISER_ID) VALUES ("+event.getIdEvent()+",'" + event.getName() + "','" + event.getAddress() + "',to_date('" + event.getDateTime() + "','YYYY-MM-DD\"T\"HH24:MI:SS'),'" + event.getDescription() + "'," + event.getOrganiser().getIdProfile() + ")";
                statement.executeQuery(sql);
                complete.setVisible(true);
                complete.setTextFill(GREEN);
                error.setVisible(false);
                name.setText("");
                address.setText("");
                date.setValue(null);
                time.setText("");
                description.setText("");

                LogManager.shutdown();
                System.setProperty("logFilename", "info.log");
                Logger logger = LogManager.getLogger();
                logger.info("Event crated successful: "+event.getIdEvent()+","+event.getName());
            } else {
                error.setVisible(true);
                error.setText("*Грешен формат!");
            }
        }
        else{
            error.setText("*Попълнете всички полета!");
            error.setVisible(true);
        }
    }


    public void back(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("organiser.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        if(Database.connection()!=null)
            Database.close();
    }
}

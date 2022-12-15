package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Organiser;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.EventRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.OrganiserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Objects;

import static eu.hansolo.tilesfx.Tile.GREEN;

public class EventController {
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

    public void create(){
        if(!Objects.equals(name.getText(), "")&& !Objects.equals(address.getText(), "")&& !Objects.equals(time.getText(), "")&& !Objects.equals(description.getText(), "")) {
            if (time.getText().contains(":")) {
                int id = EventRepository.autonumber();

                String[] arr = time.getText().split(":");
                int hour = Integer.parseInt(arr[0]);
                int minute = Integer.parseInt(arr[1]);
                LocalDateTime dateTime = LocalDateTime.of(date.getValue().getYear(), date.getValue().getMonth(), date.getValue().getDayOfMonth(), hour, minute);

                for (Organiser organiser : OrganiserRepository.get())
                    if (organiser.getIdProfile() == Profile.getProfiles().getIdProfile())
                        this.organiser = organiser;

                Event event = new Event(id, name.getText(), address.getText(), dateTime, description.getText(), organiser);
                EventRepository.add(event);

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


    public void back(ActionEvent event) {
        SceneChanger.change(event,"organiser.fxml");
    }
}

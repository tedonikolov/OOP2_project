package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.busnessLayer.services.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static eu.hansolo.tilesfx.Tile.GREEN;

public class EventController implements Initializable {
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
                EventService.create(name.getText(),address.getText(),time.getText(),description.getText(),date.getValue().getYear(), date.getValue().getMonth(), date.getValue().getDayOfMonth());

                complete.setVisible(true);
                complete.setTextFill(GREEN);
                error.setVisible(false);
                name.setText("");
                address.setText("");
                date.setValue(null);
                time.setText("");
                description.setText("");

            } else {
                complete.setVisible(false);
                error.setVisible(true);
                error.setText("*Грешен формат!");
            }
        }
        else{
            complete.setVisible(false);
            error.setText("*Попълнете всички полета!");
            error.setVisible(true);
        }
    }


    public void back(ActionEvent event) {
        SceneChanger.change(event,"organiser.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventService.disableDays(date);
    }
}

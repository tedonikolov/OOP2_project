package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Seats;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.EventRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.SeatsRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.SectorsRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static eu.hansolo.tilesfx.Tile.GREEN;
import static eu.hansolo.tilesfx.Tile.RED;

public class SeatsController implements Initializable {
    private Event curr;
    @FXML
    private ChoiceBox event;
    @FXML
    private TextField type;
    @FXML
    private TextField amount;
    @FXML
    private TextField price;
    @FXML
    private TextField ticketPerClient;
    @FXML
    private Label error;

    public void create(){
        if (event.getValue() != null && !Objects.equals(type.getText(), "") && !Objects.equals(amount.getText(), "") && !Objects.equals(price.getText(), "") && !Objects.equals(ticketPerClient.getText(), "")) {

            for (Event event: EventRepository.get()){
                if(event.getOrganiser().getIdProfile()== Profile.getProfiles().getIdProfile()&& Objects.equals(event.getName(), this.event.getValue().toString())){
                    curr=event;
                    break;
                }
            }

            int id= SeatsRepository.autonumber();
            Seats seats=new Seats(id,type.getText(),Integer.parseInt(amount.getText()),0,Double.parseDouble(price.getText()),Integer.parseInt(ticketPerClient.getText()));
            SeatsRepository.add(seats);

            id = SectorsRepository.autonumber();
            Sectors sectors = new Sectors(id,curr,seats);
            SectorsRepository.add(sectors);

            error.setText("Успешно създаден "+type.getText());
            error.setTextFill(GREEN);
            error.setVisible(true);
            clear();
            LogManager.shutdown();
            System.setProperty("logFilename", "info.log");
            Logger logger = LogManager.getLogger();
            logger.info("Seat crated successful: "+seats.getIdSeats()+","+seats.getType());
        }
        else {
            error.setText("Попълнете всички полета!");
            error.setTextFill(RED);
            error.setVisible(true);
        }
    }

    public void back(ActionEvent event){
        SceneChanger.change(event,"organiser.fxml");
    }

    public void clear(){
        type.clear();
        amount.clear();
        price.clear();
        ticketPerClient.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> events=new ArrayList<>();
        for(Event event: EventRepository.get()){
            if(event.getOrganiser().getIdProfile()== Profile.getProfiles().getIdProfile())
                events.add(event.getName());
        }
        event.getItems().addAll(events);

        event.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if(event.getValue()!=null)
                    clear();
            }
        });
    }
}

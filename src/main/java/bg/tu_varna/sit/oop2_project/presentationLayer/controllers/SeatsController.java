package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Seats;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import bg.tu_varna.sit.oop2_project.dataLayer.collections.GetEvents;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void create() throws SQLException {
        if (event.getValue() != null && !Objects.equals(type.getText(), "") && !Objects.equals(amount.getText(), "") && !Objects.equals(price.getText(), "") && !Objects.equals(ticketPerClient.getText(), "")) {
            Connection connection = Database.connection();
            Statement statement = connection.createStatement();

            for (Event event:GetEvents.get()){
                if(event.getOrganiser().getIdProfile()== Profile.getProfiles().getIdProfile()&& Objects.equals(event.getName(), this.event.getValue().toString())){
                    curr=event;
                    break;
                }
            }

            String getId = "SELECT SEATS_SEQUENCE.nextVal from DUAL";
            ResultSet rs = statement.executeQuery(getId);
            int id=0;
            if(rs.next())
                id = rs.getInt(1);

            Seats seats=new Seats(id,type.getText(),Integer.parseInt(amount.getText()),0,Double.parseDouble(price.getText()),Integer.parseInt(ticketPerClient.getText()));
            String sql ="INSERT INTO SEATS(ID_SEATS, TYPE, AMOUNT, SOLD, PRICE, TICKETPERCLIENT) VALUES ("+seats.getIdSeats()+",'"+seats.getType()+"',"+seats.getAmount()+","+seats.getSold()+","+seats.getPrice()+","+seats.getTicketPerClient()+")";
            statement.execute(sql);

            getId = "SELECT SECTORS_SEQUENCE.nextVal from DUAL";
            rs = statement.executeQuery(getId);
            if(rs.next())
                id = rs.getInt(1);

            Sectors sectors = new Sectors(id,curr,seats);
            sql ="INSERT INTO SECTORS (ID_SECTORS, EVENT_ID, SEATS_ID) VALUES ("+sectors.getIdSectors()+","+sectors.getEvent().getIdEvent()+","+sectors.getSeats().getIdSeats()+")";
            statement.execute(sql);
            error.setText("Успешно създаден "+type.getText());
            error.setTextFill(GREEN);
            error.setVisible(true);
            Database.close();
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
        if(Database.connection()!=null)
            Database.close();
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
        for(Event event: GetEvents.get()){
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

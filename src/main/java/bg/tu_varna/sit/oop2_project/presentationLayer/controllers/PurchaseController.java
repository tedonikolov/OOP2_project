package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.*;
import bg.tu_varna.sit.oop2_project.busnessLayer.services.PurchaseService;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.*;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PurchaseController implements Initializable {
    private List<Tickets> ticketsList;
    private List<Sectors> sectorsList;
    private Tickets tickets;
    private Seats seats;
    private Sectors sectors;
    @FXML
    private ComboBox eventBox;
    @FXML
    private ComboBox seatsBox;
    @FXML
    private Label seat;
    @FXML
    private Button next2;
    @FXML
    private Label price;
    @FXML
    private Label name;
    @FXML
    private Label lastname;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label amount;
    @FXML
    private TextField name1;
    @FXML
    private TextField lastname1;
    @FXML
    private TextField phone1;
    @FXML
    private TextField email1;
    @FXML
    private TextField amount1;
    @FXML
    private Button buy;
    @FXML
    private Label error;
    @FXML
    private Label left;

    public void back(ActionEvent event) throws IOException {
        SceneChanger.change(event,"distributor.fxml");
    }

    public void next(){
        if(eventBox.getValue()!=null) {
            seatsBox.getItems().clear();
            seat.setVisible(true);
            seatsBox.setVisible(true);
            next2.setVisible(true);

            PurchaseService.seats(eventBox.getValue().toString(),seatsBox);

            price.setVisible(false);
            name.setVisible(false);
            name1.setVisible(false);
            lastname.setVisible(false);
            lastname1.setVisible(false);
            email.setVisible(false);
            email1.setVisible(false);
            phone.setVisible(false);
            phone1.setVisible(false);
            amount.setVisible(false);
            amount1.setVisible(false);
            buy.setVisible(false);
            left.setVisible(false);
            error.setVisible(false);
        }
    }

    public void next2() {
        if(seatsBox.getValue()!=null) {
            PurchaseService.sectors(eventBox.getValue().toString(),seatsBox.getValue().toString(),price);

            price.setVisible(true);
            name.setVisible(true);
            name1.setVisible(true);
            lastname.setVisible(true);
            lastname1.setVisible(true);
            email.setVisible(true);
            email1.setVisible(true);
            phone.setVisible(true);
            phone1.setVisible(true);
            amount.setVisible(true);
            amount1.setVisible(true);
            buy.setVisible(true);
            name1.setText("");
            lastname1.setText("");
            email1.setText("");
            phone1.setText("");
            amount1.setText("");
            buy.setVisible(true);
            left.setVisible(false);
            error.setVisible(false);
        }
    }

    public void buy() {
        if(!Objects.equals(name1.getText(), "") && !Objects.equals(lastname1.getText(), "") && !Objects.equals(email1.getText(), "") && !Objects.equals(phone1.getText(), "") && !Objects.equals(amount1.getText(), "")) {
            if(PhoneValidator.validate(phone1.getText())) {
                if(EmailValidator.validate(email1.getText())) {
                    PurchaseService.buy(name1.getText(),lastname1.getText(),email1.getText(),phone1.getText(),Integer.parseInt(amount1.getText()),left,error);
                }else {
                    error.setVisible(true);
                    error.setText("Невалиден имейл!");
                }
            }else {
                error.setVisible(true);
                error.setText("Невалиден телефонен номер!");
            }
        }else {
            error.setVisible(true);
            error.setText("Попълнете всички полета!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PurchaseService.init(eventBox);
    }
}

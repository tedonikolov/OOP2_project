package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.*;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Organiser;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.OrganiserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

import static eu.hansolo.tilesfx.Tile.RED;

public class ProfileOrganiserController implements Initializable {
    private Organiser organiser;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private Button changeFirstName;
    @FXML
    private Button changeLastName;
    @FXML
    private Button changeEmail;
    @FXML
    private Button changePhone;
    @FXML
    private Label error;

    public void back(ActionEvent event){
        SceneChanger.change(event,"organiser.fxml");
    }

    public void changeFirstName() {
        error.setVisible(false);
        organiser.setFirstName(firstName.getText());
        OrganiserRepository.updateFirstName(organiser.getFirstName(), organiser.getIdProfile());
        firstName.promptTextProperty().setValue(organiser.getFirstName());
        firstName.setText("");
        changeFirstName.requestFocus();
        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("First name changed successful! organiser ID:"+organiser.getIdProfile());
    }

    public void changeLastName() {
        error.setVisible(false);
        organiser.setLastName(lastName.getText());
        OrganiserRepository.updateLastName(organiser.getLastName(), organiser.getIdProfile());
        lastName.promptTextProperty().setValue(organiser.getLastName());
        lastName.setText("");
        changeLastName.requestFocus();
        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("Last name changed successful! organiser ID:"+organiser.getIdProfile());
    }

    public void changeEmail() {
        if (EmailValidator.validate(email.getText())) {
            error.setVisible(false);
            organiser.setEmail(email.getText());
            OrganiserRepository.updateEmail(organiser.getEmail(), organiser.getIdProfile());
            email.promptTextProperty().setValue(organiser.getEmail());
            email.setText("");
            changeEmail.requestFocus();
            LogManager.shutdown();
            System.setProperty("logFilename", "info.log");
            Logger logger = LogManager.getLogger();
            logger.info("Email changed successful! organiser ID:" + organiser.getIdProfile());
        }else{
            error.setTextFill(RED);
            error.setText("Невалиден имейл");
            error.setVisible(true);
        }
    }

    public void changePhone() {
        if(PhoneValidator.validate(phone.getText())) {
            error.setVisible(false);
            organiser.setPhoneNumber(phone.getText());
            OrganiserRepository.updatePhone(organiser.getPhoneNumber(), organiser.getIdProfile());
            phone.promptTextProperty().setValue(organiser.getPhoneNumber());
            phone.setText("");
            changePhone.requestFocus();
            LogManager.shutdown();
            System.setProperty("logFilename", "info.log");
            Logger logger = LogManager.getLogger();
            logger.info("Phone changed successful! organiser ID:" + organiser.getIdProfile());
        }else{
            error.setTextFill(RED);
            error.setText("Невалиден телефонен номер");
            error.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Organiser organiser: OrganiserRepository.get()){
            if(organiser.getIdProfile()== Profile.getProfiles().getIdProfile()){
                this.organiser=organiser;
                firstName.promptTextProperty().setValue(organiser.getFirstName());
                lastName.promptTextProperty().setValue(organiser.getLastName());
                email.promptTextProperty().setValue(organiser.getEmail());
                phone.promptTextProperty().setValue(organiser.getPhoneNumber());
            }
        }
    }
}

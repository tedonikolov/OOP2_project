package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.*;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Distributor;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.DistributorRepository;
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

public class ProfileDistributorController implements Initializable {
    private Distributor distributor;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private Label rating;
    @FXML
    private TextField salary;
    @FXML
    private Button changeFirstName;
    @FXML
    private Button changeLastName;
    @FXML
    private Button changeEmail;
    @FXML
    private Button changePhone;
    @FXML
    private Button changeSalary;
    @FXML
    private Label error;

    public void back(ActionEvent event){
        SceneChanger.change(event,"distributor.fxml");
    }

    public void changeFirstName() {
        error.setVisible(false);
        distributor.setFirstName(firstName.getText());
        DistributorRepository.updateFirstName(distributor.getFirstName(), distributor.getIdProfile());
        firstName.promptTextProperty().setValue(distributor.getFirstName());
        firstName.setText("");
        changeFirstName.requestFocus();
        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("First name changed successful! distributor ID:"+distributor.getIdProfile());
    }

    public void changeLastName() {
        error.setVisible(false);
        distributor.setLastName(lastName.getText());
        DistributorRepository.updateLastName(distributor.getLastName(), distributor.getIdProfile());
        lastName.promptTextProperty().setValue(distributor.getLastName());
        lastName.setText("");
        changeLastName.requestFocus();
        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("Last name changed successful! distributor ID:"+distributor.getIdProfile());
    }

    public void changeEmail() {
        if(EmailValidator.validate(email.getText())) {
            error.setVisible(false);
            distributor.setEmail(email.getText());
            DistributorRepository.updateEmail(distributor.getEmail(), distributor.getIdProfile());
            email.promptTextProperty().setValue(distributor.getEmail());
            email.setText("");
            changeEmail.requestFocus();
            LogManager.shutdown();
            System.setProperty("logFilename", "info.log");
            Logger logger = LogManager.getLogger();
            logger.info("Email changed successful! distributor ID:" + distributor.getIdProfile());
        }else{
            error.setTextFill(RED);
            error.setText("Невалиден имейл");
            error.setVisible(true);
        }
    }

    public void changePhone() {
        if(PhoneValidator.validate(phone.getText())) {
            error.setVisible(false);
            distributor.setPhoneNumber(phone.getText());
            DistributorRepository.updatePhone(distributor.getPhoneNumber(), distributor.getIdProfile());
            phone.promptTextProperty().setValue(distributor.getPhoneNumber());
            phone.setText("");
            changePhone.requestFocus();
            LogManager.shutdown();
            System.setProperty("logFilename", "info.log");
            Logger logger = LogManager.getLogger();
            logger.info("Phone changed successful! distributor ID:" + distributor.getIdProfile());
        }else{
            error.setTextFill(RED);
            error.setText("Невалиден телефонен номер");
            error.setVisible(true);
        }
    }

    public void changeSalary() {
        error.setVisible(false);
        distributor.setSalary(Integer.parseInt(salary.getText()));
        DistributorRepository.updateSalary(String.valueOf(distributor.getSalary()),distributor.getIdProfile());
        salary.promptTextProperty().setValue(String.valueOf(distributor.getSalary()));
        salary.setText("");
        changeSalary.requestFocus();
        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("Salary changed successful! distributor ID:"+distributor.getIdProfile());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Distributor distributor: DistributorRepository.get()){
            if(distributor.getIdProfile()== Profile.getProfiles().getIdProfile()){
                this.distributor =distributor;
                firstName.promptTextProperty().setValue(distributor.getFirstName());
                lastName.promptTextProperty().setValue(distributor.getLastName());
                email.promptTextProperty().setValue(distributor.getEmail());
                phone.promptTextProperty().setValue(distributor.getPhoneNumber());
                rating.textProperty().setValue(String.valueOf(distributor.getRating()));
                salary.promptTextProperty().setValue(String.valueOf(distributor.getSalary()));
            }
        }
    }
}

package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.*;
import bg.tu_varna.sit.oop2_project.busnessLayer.services.RegistrationService;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.RegistrationDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static eu.hansolo.tilesfx.Tile.GREEN;
import static eu.hansolo.tilesfx.Tile.RED;

public class RegistrationController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private PasswordField pass;
    @FXML
    private PasswordField pass2;
    @FXML
    private ChoiceBox box;
    @FXML
    private Label label;
    public void registration () throws SQLException, NoSuchAlgorithmException {

        if(!Objects.equals(username.getText(), "") && !Objects.equals(firstName.getText(), "") && !Objects.equals(lastName.getText(), "") && !Objects.equals(email.getText(), "") && !Objects.equals(phone.getText(), "") && !Objects.equals(pass.getText(), "") && !Objects.equals(pass2.getText(), "")&&box.getValue()!=null) {
            if (PhoneValidator.validate(phone.getText())) {
                if(EmailValidator.validate(email.getText())) {
                    if (!Objects.equals(pass.getText(), pass2.getText())) {
                        label.setText("*Паролите не съвпадат");
                        label.setTextFill(RED);
                    } else {
                        RegistrationDTO registrationDTO=new RegistrationDTO(username.getText(),firstName.getText(),lastName.getText(),email.getText(),phone.getText(),pass.getText(),pass2.getText(),box.getValue().toString());
                        if(RegistrationService.register(registrationDTO)){
                            label.setText("*Профилът е създаден успешно");
                            label.setTextFill(GREEN);
                        } else {
                            label.setText("*Съществува такъв профил");
                            label.setTextFill(RED);
                        }
                        username.clear();
                        firstName.clear();
                        lastName.clear();
                        email.clear();
                        phone.clear();
                        pass.clear();
                        pass2.clear();
                        box.setValue("");
                    }
                }else {
                    label.setText("*Невалиден имейл");
                    label.setTextFill(RED);
                }
            }else {
                label.setText("*Невалиден телефонен номер");
                label.setTextFill(RED);
            }
        }
        else{
            label.setText("*Попълнете всички полета");
            label.setTextFill(RED);
        }
    }

    public void back(ActionEvent event){
        SceneChanger.change(event,"admin.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RegistrationService.init(box);
    }
}

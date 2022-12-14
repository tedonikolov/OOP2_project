package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.services.LoginService;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class LoginController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label label;

    public void login(ActionEvent event) throws NoSuchAlgorithmException {
        if(!LoginService.login(new LoginDTO(username.getText(), password.getText()),event)){
            label.setText("*Грешна парола или потребител!");
            label.setVisible(true);
        }
    }

    public void exit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LogManager.shutdown();
        File file=new File("date.log");
        file.delete();
        file=new File("info.log");
        file.delete();
        System.setProperty("logFilename", "date.log");
        Logger logger = LogManager.getLogger();
        logger.info("");
    }

}
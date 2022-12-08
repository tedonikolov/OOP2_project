package bg.tu_varna.sit.oop2_project.controllers;

import bg.tu_varna.sit.oop2_project.backend.Database;
import bg.tu_varna.sit.oop2_project.EventOrganizer;
import bg.tu_varna.sit.oop2_project.backend.EmailValidator;
import bg.tu_varna.sit.oop2_project.backend.PhoneValidator;
import bg.tu_varna.sit.oop2_project.backend.Profile;
import bg.tu_varna.sit.oop2_project.entities.Organiser;
import bg.tu_varna.sit.oop2_project.backend.collections.GetOrganisers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileOrganiserController implements Initializable {
    private Stage stage;
    private Scene scene;
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

    public void back(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("organiser.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        if(Database.connection()!=null)
            Database.close();
    }

    public void changeFirstName() throws SQLException {
        organiser.setFirstName(firstName.getText());
        Connection connection = Database.connection();
        PreparedStatement statement = connection.prepareStatement("UPDATE ORGANISER SET FIRSTNAME='"+ organiser.getFirstName() +"' WHERE ID_PROFILE=" + organiser.getIdProfile());
        ResultSet result = statement.executeQuery();
        Database.close();
        firstName.promptTextProperty().setValue(organiser.getFirstName());
        firstName.setText("");
        changeFirstName.requestFocus();
        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("First name changed successful! distributor ID:"+organiser.getIdProfile());
    }

    public void changeLastName() throws SQLException {
        organiser.setLastName(lastName.getText());
        Connection connection = Database.connection();
        PreparedStatement statement = connection.prepareStatement("UPDATE ORGANISER SET LASTNAME='"+ organiser.getLastName() +"' WHERE ID_PROFILE=" + organiser.getIdProfile());
        ResultSet result = statement.executeQuery();
        Database.close();
        lastName.promptTextProperty().setValue(organiser.getLastName());
        lastName.setText("");
        changeLastName.requestFocus();
        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("Last name changed successful! distributor ID:"+organiser.getIdProfile());
    }

    public void changeEmail() throws SQLException {
        if (EmailValidator.validate(email.getText())) {
            organiser.setEmail(email.getText());
            Connection connection = Database.connection();
            PreparedStatement statement = connection.prepareStatement("UPDATE ORGANISER SET EMAIL='" + organiser.getEmail() + "' WHERE ID_PROFILE=" + organiser.getIdProfile());
            ResultSet result = statement.executeQuery();
            Database.close();
            email.promptTextProperty().setValue(organiser.getEmail());
            email.setText("");
            changeEmail.requestFocus();
            LogManager.shutdown();
            System.setProperty("logFilename", "info.log");
            Logger logger = LogManager.getLogger();
            logger.info("Email changed successful! distributor ID:" + organiser.getIdProfile());
        }
    }

    public void changePhone() throws SQLException {
        if(PhoneValidator.validate(phone.getText())) {
            organiser.setPhoneNumber(phone.getText());
            Connection connection = Database.connection();
            PreparedStatement statement = connection.prepareStatement("UPDATE ORGANISER SET PHONE='" + organiser.getPhoneNumber() + "' WHERE ID_PROFILE=" + organiser.getIdProfile());
            ResultSet result = statement.executeQuery();
            Database.close();
            phone.promptTextProperty().setValue(organiser.getPhoneNumber());
            phone.setText("");
            changePhone.requestFocus();
            LogManager.shutdown();
            System.setProperty("logFilename", "info.log");
            Logger logger = LogManager.getLogger();
            logger.info("Phone changed successful! distributor ID:" + organiser.getIdProfile());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Organiser organiser: GetOrganisers.get()){
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

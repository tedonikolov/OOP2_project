package bg.tu_varna.sit.oop2_project.controllers;

import bg.tu_varna.sit.oop2_project.backend.Database;
import bg.tu_varna.sit.oop2_project.EventOrganizer;
import bg.tu_varna.sit.oop2_project.backend.EmailValidator;
import bg.tu_varna.sit.oop2_project.backend.PhoneValidator;
import bg.tu_varna.sit.oop2_project.backend.Profile;
import bg.tu_varna.sit.oop2_project.entities.Distributor;
import bg.tu_varna.sit.oop2_project.backend.collections.GetDistributors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class ProfileDistributorController implements Initializable {
    private Stage stage;
    private Scene scene;

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

    public void back(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("distributor.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        if(Database.connection()!=null)
            Database.close();
    }

    public void changeFirstName() throws SQLException {
        distributor.setFirstName(firstName.getText());
        Connection connection = Database.connection();
        PreparedStatement statement = connection.prepareStatement("UPDATE DISTRIBUTOR SET FIRSTNAME='"+ distributor.getFirstName() +"' WHERE ID_PROFILE=" + distributor.getIdProfile());
        ResultSet result = statement.executeQuery();
        Database.close();
        firstName.promptTextProperty().setValue(distributor.getFirstName());
        firstName.setText("");
        changeFirstName.requestFocus();
        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("First name changed successful! distributor ID:"+distributor.getIdProfile());
    }

    public void changeLastName() throws SQLException {
        distributor.setLastName(lastName.getText());
        Connection connection = Database.connection();
        PreparedStatement statement = connection.prepareStatement("UPDATE DISTRIBUTOR SET LASTNAME='"+ distributor.getLastName() +"' WHERE ID_PROFILE=" + distributor.getIdProfile());
        ResultSet result = statement.executeQuery();
        Database.close();
        lastName.promptTextProperty().setValue(distributor.getLastName());
        lastName.setText("");
        changeLastName.requestFocus();
        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("Last name changed successful! distributor ID:"+distributor.getIdProfile());
    }

    public void changeEmail() throws SQLException {
        if(EmailValidator.validate(email.getText())) {
            distributor.setEmail(email.getText());
            Connection connection = Database.connection();
            PreparedStatement statement = connection.prepareStatement("UPDATE DISTRIBUTOR SET EMAIL='" + distributor.getEmail() + "' WHERE ID_PROFILE=" + distributor.getIdProfile());
            ResultSet result = statement.executeQuery();
            Database.close();
            email.promptTextProperty().setValue(distributor.getEmail());
            email.setText("");
            changeEmail.requestFocus();
            LogManager.shutdown();
            System.setProperty("logFilename", "info.log");
            Logger logger = LogManager.getLogger();
            logger.info("Email changed successful! distributor ID:" + distributor.getIdProfile());
        }
    }

    public void changePhone() throws SQLException {
        if(PhoneValidator.validate(phone.getText())) {
            distributor.setPhoneNumber(phone.getText());
            Connection connection = Database.connection();
            PreparedStatement statement = connection.prepareStatement("UPDATE DISTRIBUTOR SET PHONE='" + distributor.getPhoneNumber() + "' WHERE ID_PROFILE=" + distributor.getIdProfile());
            ResultSet result = statement.executeQuery();
            Database.close();
            phone.promptTextProperty().setValue(distributor.getPhoneNumber());
            phone.setText("");
            changePhone.requestFocus();
            LogManager.shutdown();
            System.setProperty("logFilename", "info.log");
            Logger logger = LogManager.getLogger();
            logger.info("Phone changed successful! distributor ID:" + distributor.getIdProfile());
        }
    }

    public void changeSalary() throws SQLException {
        distributor.setSalary(Integer.parseInt(salary.getText()));
        Connection connection = Database.connection();
        PreparedStatement statement = connection.prepareStatement("UPDATE DISTRIBUTOR SET SALARY="+ distributor.getSalary() +" WHERE ID_PROFILE=" + distributor.getIdProfile());
        ResultSet result = statement.executeQuery();
        Database.close();
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
        for (Distributor distributor: GetDistributors.get()){
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

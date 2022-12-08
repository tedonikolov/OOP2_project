package bg.tu_varna.sit.oop2_project.controllers;

import bg.tu_varna.sit.oop2_project.backend.Database;
import bg.tu_varna.sit.oop2_project.backend.PasswordHash;
import bg.tu_varna.sit.oop2_project.backend.Profile;
import bg.tu_varna.sit.oop2_project.backend.SceneChanger;
import bg.tu_varna.sit.oop2_project.entities.Roles;
import bg.tu_varna.sit.oop2_project.entities.Profiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import bg.tu_varna.sit.oop2_project.backend.collections.GetProfiles;
import bg.tu_varna.sit.oop2_project.backend.collections.GetRoles;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class LoginController implements Initializable {
    @FXML
    private ChoiceBox box;
    @FXML
    private PasswordField password;
    @FXML
    private Label label;

    public void login(ActionEvent event) throws SQLException, NoSuchAlgorithmException {
        if(box.getValue()!=null) {
            Connection connection = Database.connection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PROFILES JOIN ROLES ON ROLES.ID_ROLE=PROFILES.ROLE_ID WHERE USERNAME='" + box.getValue().toString() + "'");
            ResultSet result = statement.executeQuery();

            Profiles profiles = null;
            while (result.next()) {
                profiles = new Profiles(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), new Roles(Integer.parseInt(result.getString(5)), result.getString(6)));
            }
            Database.close();

            String hashedPassword= PasswordHash.hashing(password.getText());

            if (Objects.equals(profiles.getPassword(), hashedPassword)) {
                if (profiles.getRoles().getIdRole() == GetRoles.get().get(0).getIdRole()) {
                    SceneChanger.change(event,"admin.fxml");
                }
                else if (profiles.getRoles().getIdRole() == GetRoles.get().get(1).getIdRole()) {
                    Profile.setProfiles(profiles);
                    SceneChanger.change(event,"organiser.fxml");
                }
                else{
                    Profile.setProfiles(profiles);
                    SceneChanger.change(event,"distributor.fxml");
                }
                LogManager.shutdown();
                System.setProperty("logFilename", "info.log");
                Logger logger = LogManager.getLogger();
                logger.info(profiles.getUsername()+" logged in! Role:"+profiles.getRoles().getRole());
            } else {
                label.setText("*Грешна парола!");
            }
        }
        else{
            label.setText("*изберете потребител!");
        }
        label.setVisible(true);
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

        List<String> usernames=new ArrayList<>();
        for(Profiles profiles : GetProfiles.get()){
            usernames.add(profiles.getUsername());
        }
        box.getItems().addAll(usernames);
    }

}
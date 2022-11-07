package bg.tu_varna.sit.oop2_project;

import entity.Roles;
import entity.SelectAll;
import entity.Profiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
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
    private Stage stage;
    private Scene scene;

    public void login(ActionEvent event) throws SQLException, IOException, NoSuchAlgorithmException {
        if(box.getValue()!=null) {
            Connection connection = Database.connection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PROFILES JOIN ROLES ON ROLES.ID_ROLE=PROFILES.ROLE_ID WHERE USERNAME='" + box.getValue().toString() + "'");
            ResultSet result = statement.executeQuery();

            Profiles profiles = null;
            while (result.next()) {
                profiles = new Profiles(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), new Roles(Integer.parseInt(result.getString(5)), result.getString(6)));
            }
            Database.close();

            String hashedPassword=PasswordHash.hashing(password.getText());

            if (Objects.equals(profiles.getPassword(), hashedPassword)) {
                if (profiles.getRoles().getIdRole() == ((List<Roles>) SelectAll.getAll("ROLES")).get(0).getIdRole()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("admin.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                label.setText("*Грешна парола!");
            }
        }
        else{
            label.setText("*изберете потребител!");
        }
        label.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<String> usernames=new ArrayList<>();
            for(Profiles profiles : (List<Profiles>)SelectAll.getAll("PROFILES")){
                usernames.add(profiles.getUsername());
            }
            box.getItems().addAll(usernames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
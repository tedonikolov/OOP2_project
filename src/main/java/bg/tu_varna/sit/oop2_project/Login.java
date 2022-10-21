package bg.tu_varna.sit.oop2_project;

import entity.GetRoles;
import entity.Profile;
import entity.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Login {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label label;
    private Stage stage;
    private Scene scene;

    public void login(ActionEvent event) throws SQLException, IOException {
        Connection connection= Database.connection();
        ResultSet result = connection.prepareStatement("SELECT * FROM PROFILES ").executeQuery();

        Profile profile=null;
        while (result.next()){
            for (Role role:GetRoles.getRoles()){
                if(role.getId_role()==Integer.parseInt(result.getString(4))){
                    profile=new Profile(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3),role);
                }
            }
        }
        if(profile!=null&&Objects.equals(profile.getUsername(), username.getText()) && Objects.equals(profile.getPassword(), password.getText())){
            if( profile.getRole()==GetRoles.getRoles().get(0)){
                FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("admin.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
        }
        else {
            label.setText("*Грешна парола или потребителско име");
        }
        label.setVisible(true);
    }

}
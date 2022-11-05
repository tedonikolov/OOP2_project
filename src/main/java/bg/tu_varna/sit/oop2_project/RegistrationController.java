package bg.tu_varna.sit.oop2_project;

import entity.SelectAll;
import entity.Roles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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

    public void registration () throws SQLException {

        if(!Objects.equals(username.getText(), "") && !Objects.equals(firstName.getText(), "") && !Objects.equals(lastName.getText(), "") && !Objects.equals(email.getText(), "") && !Objects.equals(phone.getText(), "") && !Objects.equals(pass.getText(), "") && !Objects.equals(pass2.getText(), "")) {
            if (!Objects.equals(pass.getText(), pass2.getText())) {
                label.setText("*Паролите не съвпадат");
            }
            else {
                label.setText("*Профилът е създаден успешно");
            }
        }
        else{
            label.setText("*Попълнете всички полета");
        }
    }

    public void back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("admin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<String> roles=new ArrayList<>();
            for(Roles role: (List<Roles>)SelectAll.getAll("ROLES")){
                roles.add(role.getRole());
            }
            box.getItems().add(roles.get(1));
            box.getItems().add(roles.get(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

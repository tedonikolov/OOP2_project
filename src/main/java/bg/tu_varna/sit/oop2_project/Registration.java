package bg.tu_varna.sit.oop2_project;

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
import java.util.Objects;

public class Registration {
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
    private Label label;

    public void registration (ActionEvent event) throws IOException {
        if(!Objects.equals(username.getText(), "") && !Objects.equals(firstName.getText(), "") && !Objects.equals(lastName.getText(), "") && !Objects.equals(email.getText(), "") && !Objects.equals(phone.getText(), "") && !Objects.equals(pass.getText(), "") && !Objects.equals(pass2.getText(), "")) {
            if (!Objects.equals(pass.getText(), pass2.getText())) {
                label.setText("*Паролите не съвпадат");
                label.setVisible(true);
            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("login.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
        }
        else{
            label.setText("*Попълнете всички полета");
            label.setVisible(true);
        }
    }
}

package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.services.AdminService;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.ProfilesDTO;
import bg.tu_varna.sit.oop2_project.EventOrganizer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static eu.hansolo.tilesfx.Tile.RED;

public class AdminController {
    @FXML
    private AnchorPane stage;
    @FXML
    private TableView<ProfilesDTO> table;
    @FXML
    private Button button;
    @FXML
    private TextField id;
    @FXML
    private TextField id1;
    @FXML
    private Label label;
    @FXML
    private Label exception;
    @FXML
    private Button button1;
    @FXML
    private PasswordField oldPass;
    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField newPass2;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label error;
    @FXML
    private Separator line;

    public void createProfile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("registration.fxml"));
        Stage stage1 = (Stage) stage.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage1.setScene(scene);
        stage1.show();
    }

    public void loginScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("login.fxml"));
        Stage stage1 = (Stage) stage.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage1.setScene(scene);
        stage1.show();
    }

    public void showProfiles(){
        table.getItems().clear();
        label.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        button.setVisible(false);
        button1.setVisible(false);
        oldPass.setVisible(false);
        newPass.setVisible(false);
        newPass2.setVisible(false);
        error.setVisible(false);
        line.setVisible(false);
        id.setVisible(false);
        id1.setVisible(false);
        exception.setVisible(false);

        AdminService.show(table);
        table.setVisible(true);
    }

    public void profilesControl(){
        table.setVisible(false);
        label.setVisible(true);
        label1.setVisible(true);
        label2.setVisible(true);
        label3.setVisible(true);
        label4.setVisible(true);
        label5.setVisible(true);
        label6.setVisible(true);
        button.setVisible(true);
        button1.setVisible(true);
        oldPass.setVisible(true);
        newPass.setVisible(true);
        newPass2.setVisible(true);
        line.setVisible(true);
        id.setVisible(true);
        id1.setVisible(true);
    }

    public void removeProfile(){
        AdminService.delete(Integer.parseInt(id.getText()),exception);
        exception.setVisible(true);
        id.clear();
    }

    public void password() throws SQLException, NoSuchAlgorithmException {
        if(!AdminService.changePass(Integer.parseInt(id1.getText()),oldPass.getText(),newPass.getText(),newPass2.getText(),error)){
            error.setTextFill(RED);
            error.setText("НЕ съществува такъв потребител!");
        }
        id1.clear();
        oldPass.clear();
        newPass.clear();
        newPass2.clear();
        error.setVisible(true);
    }
}

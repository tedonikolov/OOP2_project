package controllers;

import bg.tu_varna.sit.oop2_project.Database;
import bg.tu_varna.sit.oop2_project.EventOrganizer;
import bg.tu_varna.sit.oop2_project.PasswordHash;
import entities.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import collections.GetDistributors;
import collections.GetOrganisers;
import collections.GetProfiles;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static eu.hansolo.tilesfx.Tile.GREEN;
import static eu.hansolo.tilesfx.Tile.RED;

public class AdminController {
    @FXML
    private AnchorPane stage;
    @FXML
    private TableView<Profiles> table;
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

    public void showProfiles() throws SQLException {
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

        TableColumn idProfile = new TableColumn<List<String>,String>("№");
        idProfile.setCellValueFactory(new PropertyValueFactory<Profiles, Integer>("idProfile"));

        TableColumn username = new TableColumn<Profiles, String>("Потребител");
        username.setCellValueFactory(new PropertyValueFactory<Profiles, String >("username"));

        TableColumn role = new TableColumn<Profiles, String>("Роля");
        role.setCellValueFactory(new PropertyValueFactory<Profiles, String>("role"));

        table.getColumns().setAll(idProfile,username,role);

        for(Profiles profiles: GetProfiles.get()){
            table.getItems().add(profiles);
        }


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

    public void removeProfile() throws SQLException {
        try {
            boolean flag=false;

            for(Organiser organiser: GetOrganisers.get()){
                if(organiser.getIdProfile()==Integer.parseInt(id.getText())){
                    Connection connection= Database.connection();
                    PreparedStatement statement = connection.prepareStatement("DELETE FROM ORGANISER WHERE ID_PROFILE="+Integer.parseInt(id.getText()));
                    ResultSet result = statement.executeQuery();
                    flag=true;
                    Database.close();
                    break;
                }
            }

            if(!flag){
                for(Distributor distributor: GetDistributors.get()){
                    if(distributor.getIdProfile()==Integer.parseInt(id.getText())){
                        Connection connection=Database.connection();
                        PreparedStatement statement = connection.prepareStatement("DELETE FROM DISTRIBUTOR WHERE ID_PROFILE="+Integer.parseInt(id.getText()));
                        ResultSet result = statement.executeQuery();
                        Database.close();
                        break;
                    }
                }
            }

            Connection connection=Database.connection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM PROFILES WHERE ID_PROFILE=" + Integer.parseInt(id.getText()));
            ResultSet result = statement.executeQuery();
            Database.close();
            exception.setTextFill(GREEN);
            exception.setText("Потребителят е изтрит успешно");
            exception.setVisible(true);
        }
        catch (SQLException e) {
            exception.setTextFill(RED);
            exception.setText("Не можете да изтриете потребителя!");
            exception.setVisible(true);
            System.out.println(e);
        }
    }

    public void password() throws SQLException, NoSuchAlgorithmException {
        boolean flag=false;

        for(Profiles profiles: GetProfiles.get()){
            if(Integer.parseInt(id1.getText())==profiles.getIdProfile()){
                String old= PasswordHash.hashing(oldPass.getText());
                if(old.equals(profiles.getPassword())){
                    if (!Objects.equals(newPass.getText(), newPass2.getText())) {
                        error.setText("*Паролите не съвпадат");
                        error.setTextFill(RED);
                    }
                    else {
                        String pass=PasswordHash.hashing(newPass.getText());
                        Connection connection = Database.connection();
                        PreparedStatement statement = connection.prepareStatement("UPDATE PROFILES SET PASSWORD='"+pass+"' WHERE ID_PROFILE=" + Integer.parseInt(id1.getText()));
                        ResultSet result = statement.executeQuery();
                        Database.close();
                        error.setTextFill(GREEN);
                        error.setText("Успешно променихте паролата");
                    }
                }
                else {
                    error.setTextFill(RED);
                    error.setText("Грешна стара парола!");
                }
                flag=true;
                break;
            }
        }
        if(!flag){
            error.setTextFill(RED);
            error.setText("НЕ съществува такъв потребител!");
        }
        error.setVisible(true);
    }
}

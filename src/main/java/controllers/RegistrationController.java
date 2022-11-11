package controllers;

import bg.tu_varna.sit.oop2_project.Database;
import bg.tu_varna.sit.oop2_project.EventOrganizer;
import bg.tu_varna.sit.oop2_project.PasswordHash;
import entities.*;
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
import collections.GetRoles;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
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
    public void registration () throws SQLException, NoSuchAlgorithmException {

        if(!Objects.equals(username.getText(), "") && !Objects.equals(firstName.getText(), "") && !Objects.equals(lastName.getText(), "") && !Objects.equals(email.getText(), "") && !Objects.equals(phone.getText(), "") && !Objects.equals(pass.getText(), "") && !Objects.equals(pass2.getText(), "")&&box.getValue()!=null) {
            if (!Objects.equals(pass.getText(), pass2.getText())) {
                label.setText("*Паролите не съвпадат");
            }
            else {

                String hashedPassword= PasswordHash.hashing(pass2.getText());

                for(Roles roles : GetRoles.get()){
                    if(Objects.equals(box.getValue().toString(), roles.getRole())){
                        Connection connection= Database.connection();
                        Statement statement=connection.createStatement();

                        String getId = "SELECT PROFILES_SEQUENCE.nextVal from DUAL";
                        ResultSet rs = statement.executeQuery(getId);
                        int id=0;
                        if(rs.next())
                            id = rs.getInt(1)+1;

                        Profiles profiles=new Profiles(id,username.getText(),hashedPassword,roles);
                        String sql="INSERT INTO PROFILES(USERNAME,PASSWORD,ROLE_ID) VALUES ('"+profiles.getUsername()+"','"+profiles.getPassword()+"',"+profiles.getRoles().getIdRole()+")";
                        statement.executeQuery(sql);

                        if(Objects.equals(roles.getRole(), "организатор")){
                            Organiser organiser=new Organiser(profiles,firstName.getText(),lastName.getText(),email.getText(),phone.getText());
                            sql="INSERT INTO ORGANISER(ID_PROFILE,FIRSTNAME,LASTNAME,EMAIL,PHONE) VALUES ("+organiser.getIdProfile()+",'"+organiser.getFirstName()+"','"+organiser.getLastName()+"','"+organiser.getEmail()+"','"+organiser.getPhoneNumber()+"')";
                            statement.executeQuery(sql);
                        }
                        else{
                            Distributor distributor=new Distributor(profiles,firstName.getText(),lastName.getText(),email.getText(),phone.getText(),0,0);
                            sql="INSERT INTO DISTRIBUTOR(ID_PROFILE,FIRSTNAME,LASTNAME,EMAIL,PHONE,RATING,SALARY) VALUES ("+distributor.getIdProfile()+",'"+distributor.getFirstName()+"','"+distributor.getLastName()+"','"+distributor.getEmail()+"','"+distributor.getPhoneNumber()+"',"+distributor.getRating()+","+distributor.getSalary()+")";
                            statement.executeQuery(sql);
                        }

                        label.setText("*Профилът е създаден успешно");
                    }
                }
                Database.close();
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
            for(Roles role: GetRoles.get()){
                roles.add(role.getRole());
            }
            box.getItems().add(roles.get(1));
            box.getItems().add(roles.get(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

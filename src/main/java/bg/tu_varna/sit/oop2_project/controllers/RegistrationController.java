package bg.tu_varna.sit.oop2_project.controllers;

import bg.tu_varna.sit.oop2_project.backend.Database;
import bg.tu_varna.sit.oop2_project.EventOrganizer;
import bg.tu_varna.sit.oop2_project.backend.PasswordHash;
import bg.tu_varna.sit.oop2_project.backend.PhoneValidate;
import bg.tu_varna.sit.oop2_project.backend.Profile;
import bg.tu_varna.sit.oop2_project.backend.collections.GetProfiles;
import bg.tu_varna.sit.oop2_project.entities.Distributor;
import bg.tu_varna.sit.oop2_project.entities.Organiser;
import bg.tu_varna.sit.oop2_project.entities.Profiles;
import bg.tu_varna.sit.oop2_project.entities.Roles;
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
import bg.tu_varna.sit.oop2_project.backend.collections.GetRoles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static eu.hansolo.tilesfx.Tile.GREEN;
import static eu.hansolo.tilesfx.Tile.RED;

public class RegistrationController implements Initializable {
    private List<Profiles> profiles;
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
            if (PhoneValidate.phone(phone.getText())) {
                if (!Objects.equals(pass.getText(), pass2.getText())) {
                    label.setText("*Паролите не съвпадат");
                    label.setTextFill(RED);
                } else {
                    boolean flag = true;
                    for (Profiles profiles : this.profiles) {
                        if (Objects.equals(profiles.getUsername(), username.getText())) {
                            flag = false;
                            break;
                        }
                    }
                    String hashedPassword = PasswordHash.hashing(pass2.getText());
                    if (flag) {
                        for (Roles roles : GetRoles.get()) {
                            if (Objects.equals(box.getValue().toString(), roles.getRole())) {
                                Connection connection = Database.connection();
                                Statement statement = connection.createStatement();

                                String getId = "SELECT PROFILES_SEQUENCE.nextVal from DUAL";
                                ResultSet rs = statement.executeQuery(getId);
                                int id = 0;
                                if (rs.next())
                                    id = rs.getInt(1);

                                Profiles profiles = new Profiles(id, username.getText(), hashedPassword, roles);
                                String sql = "INSERT INTO PROFILES(ID_PROFILE,USERNAME,PASSWORD,ROLE_ID) VALUES (" + profiles.getIdProfile() + ",'" + profiles.getUsername() + "','" + profiles.getPassword() + "'," + profiles.getRoles().getIdRole() + ")";
                                statement.executeQuery(sql);

                                if (Objects.equals(roles.getRole(), "организатор")) {
                                    Organiser organiser = new Organiser(profiles, firstName.getText(), lastName.getText(), email.getText(), phone.getText());
                                    sql = "INSERT INTO ORGANISER(ID_PROFILE,FIRSTNAME,LASTNAME,EMAIL,PHONE) VALUES (" + organiser.getIdProfile() + ",'" + organiser.getFirstName() + "','" + organiser.getLastName() + "','" + organiser.getEmail() + "','" + organiser.getPhoneNumber() + "')";
                                    statement.executeQuery(sql);
                                } else {
                                    Distributor distributor = new Distributor(profiles, firstName.getText(), lastName.getText(), email.getText(), phone.getText(), 0, 0);
                                    sql = "INSERT INTO DISTRIBUTOR(ID_PROFILE,FIRSTNAME,LASTNAME,EMAIL,PHONE,RATING,SALARY) VALUES (" + distributor.getIdProfile() + ",'" + distributor.getFirstName() + "','" + distributor.getLastName() + "','" + distributor.getEmail() + "','" + distributor.getPhoneNumber() + "'," + distributor.getRating() + "," + distributor.getSalary() + ")";
                                    statement.executeQuery(sql);
                                }

                                LogManager.shutdown();
                                System.setProperty("logFilename", "info.log");
                                Logger logger = LogManager.getLogger();
                                logger.info("Profile crated successful: " + profiles.getIdProfile() + "," + profiles.getUsername() + "," + profiles.getRoles().getRole());
                                label.setText("*Профилът е създаден успешно");
                                label.setTextFill(GREEN);
                            }
                        }
                        Database.close();
                    } else {
                        label.setText("*Съществува такъв профил");
                        label.setTextFill(RED);
                    }
                }
            }else {
                label.setText("*Невалиден телефонен номер");
                label.setTextFill(RED);
            }
        }
        else{
            label.setText("*Попълнете всички полета");
            label.setTextFill(RED);
        }
    }

    public void back(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("admin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        if(Database.connection()!=null)
            Database.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profiles= GetProfiles.get();
        List<String> roles=new ArrayList<>();
        for(Roles role: GetRoles.get()){
            roles.add(role.getRole());
        }
        box.getItems().add(roles.get(1));
        box.getItems().add(roles.get(2));
    }
}

package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.PasswordHash;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.RegistrationDTO;
import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.collections.GetProfiles;
import bg.tu_varna.sit.oop2_project.dataLayer.collections.GetRoles;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Distributor;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Organiser;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Roles;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistrationService {
    private static List<Profiles> profiles;
    public static void init(ChoiceBox box){
        profiles=GetProfiles.get();
        List<String> roles=new ArrayList<>();
        for(Roles role: GetRoles.get()){
            roles.add(role.getRole());
        }
        box.getItems().add(roles.get(1));
        box.getItems().add(roles.get(2));
    }

    public static boolean register(RegistrationDTO registrationDTO) throws SQLException, NoSuchAlgorithmException {
        boolean flag = true;
        for (Profiles profiles : profiles) {
            if (Objects.equals(profiles.getUsername(),registrationDTO.getUsername())) {
                flag = false;
                break;
            }
        }
        String hashedPassword = PasswordHash.hashing(registrationDTO.getPass());
        if (flag) {
            for (Roles roles : GetRoles.get()) {
                if (Objects.equals(registrationDTO.getRole(), roles.getRole())) {
                    Connection connection = Database.connection();
                    Statement statement = connection.createStatement();

                    String getId = "SELECT PROFILES_SEQUENCE.nextVal from DUAL";
                    ResultSet rs = statement.executeQuery(getId);
                    int id = 0;
                    if (rs.next())
                        id = rs.getInt(1);

                    Profiles profiles = new Profiles(id, registrationDTO.getUsername(), hashedPassword, roles);
                    String sql = "INSERT INTO PROFILES(ID_PROFILE,USERNAME,PASSWORD,ROLE_ID) VALUES (" + profiles.getIdProfile() + ",'" + profiles.getUsername() + "','" + profiles.getPassword() + "'," + profiles.getRoles().getIdRole() + ")";
                    statement.executeQuery(sql);

                    if (Objects.equals(roles.getRole(), "организатор")) {
                        Organiser organiser = new Organiser(profiles, registrationDTO.getFirstName(), registrationDTO.getLastName(), registrationDTO.getEmail(), registrationDTO.getPhone());
                        sql = "INSERT INTO ORGANISER(ID_PROFILE,FIRSTNAME,LASTNAME,EMAIL,PHONE) VALUES (" + organiser.getIdProfile() + ",'" + organiser.getFirstName() + "','" + organiser.getLastName() + "','" + organiser.getEmail() + "','" + organiser.getPhoneNumber() + "')";
                        statement.executeQuery(sql);
                    } else {
                        Distributor distributor = new Distributor(profiles, registrationDTO.getFirstName(), registrationDTO.getLastName(), registrationDTO.getEmail(), registrationDTO.getPhone(), 0, 0);
                        sql = "INSERT INTO DISTRIBUTOR(ID_PROFILE,FIRSTNAME,LASTNAME,EMAIL,PHONE,RATING,SALARY) VALUES (" + distributor.getIdProfile() + ",'" + distributor.getFirstName() + "','" + distributor.getLastName() + "','" + distributor.getEmail() + "','" + distributor.getPhoneNumber() + "'," + distributor.getRating() + "," + distributor.getSalary() + ")";
                        statement.executeQuery(sql);
                    }
                    statement.close();
                    LogManager.shutdown();
                    System.setProperty("logFilename", "info.log");
                    Logger logger = LogManager.getLogger();
                    logger.info("Profile crated successful: " + profiles.getIdProfile() + "," + profiles.getUsername() + "," + profiles.getRoles().getRole());
                }
            }
        }
        return flag;
    }
}

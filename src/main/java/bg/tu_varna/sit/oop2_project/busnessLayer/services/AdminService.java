package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.PasswordHash;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.ProfilesDTO;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.DistributorRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.OrganiserRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.ProfilesRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Distributor;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Organiser;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static eu.hansolo.tilesfx.Tile.GREEN;
import static eu.hansolo.tilesfx.Tile.RED;

public class AdminService {
    public static void show(TableView<ProfilesDTO> table){
        TableColumn idProfile = new TableColumn<List<String>,String>("№");
        idProfile.setCellValueFactory(new PropertyValueFactory<ProfilesDTO, Integer>("idProfile"));

        TableColumn username = new TableColumn<ProfilesDTO, String>("Потребител");
        username.setCellValueFactory(new PropertyValueFactory<Profiles, String >("username"));

        TableColumn role = new TableColumn<ProfilesDTO, String>("Роля");
        role.setCellValueFactory(new PropertyValueFactory<Profiles, String>("role"));

        table.getColumns().setAll(idProfile,username,role);

        for(Profiles profile: ProfilesRepository.get()){
            if(profile.getIdProfile()==1)
                continue;
            table.getItems().add(new ProfilesDTO(profile.getIdProfile(), profile.getUsername(), profile.getRoles().getRole()));
        }
    }

    public static void delete(int id, Label exception){
        try {
            boolean flag=false;

            for(Organiser organiser: OrganiserRepository.get()){
                if(organiser.getIdProfile()==id){
                    OrganiserRepository.remove(id);
                    flag = true;
                    break;
                }
            }

            if(!flag){
                for(Distributor distributor: DistributorRepository.get()){
                    if(distributor.getIdProfile()==id){
                        DistributorRepository.remove(id);
                        flag = true;
                        break;
                    }
                }
            }
            if(flag) {
                ProfilesRepository.remove(id);

                exception.setTextFill(GREEN);
                exception.setText("Потребителят е изтрит успешно");

                LogManager.shutdown();
                System.setProperty("logFilename", "info.log");
                Logger logger = LogManager.getLogger();
                logger.info("User deleted successful! profile ID:" + id);
            }
            else {
                exception.setTextFill(RED);
                exception.setText("Не съществува такъв потребител");
            }
        }
        catch (SQLException e) {
            exception.setTextFill(RED);
            exception.setText("Не можете да изтриете потребителя!");
            exception.setVisible(true);
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger("Can't delete profile");
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static boolean changePass(int id, String oldPass, String newPass, String newPass2, Label error) throws NoSuchAlgorithmException {
        for(Profiles profiles: ProfilesRepository.get()){
            if(id==profiles.getIdProfile()){
                String old= PasswordHash.hashing(oldPass);
                if(old.equals(profiles.getPassword())){
                    if (!Objects.equals(newPass, newPass2)) {
                        error.setText("*Паролите не съвпадат");
                        error.setTextFill(RED);
                    }
                    else {
                        String pass=PasswordHash.hashing(newPass);
                        ProfilesRepository.updatePassword(pass,id);
                        error.setTextFill(GREEN);
                        error.setText("Успешно променихте паролата");

                        LogManager.shutdown();
                        System.setProperty("logFilename", "info.log");
                        Logger logger = LogManager.getLogger();
                        logger.info("Password changed successful! profile ID:"+profiles.getIdProfile());
                    }
                }
                else {
                    error.setTextFill(RED);
                    error.setText("Грешна стара парола!");
                }
                return true;
            }
        }
        return false;
    }
}

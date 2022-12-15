package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.PasswordHash;
import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.LoginDTO;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.ProfilesRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.RolesRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;
import javafx.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class LoginService {
    public static boolean login(LoginDTO loginDTO, ActionEvent event) throws NoSuchAlgorithmException {
        for (Profiles profiles : ProfilesRepository.get()) {
            if (Objects.equals(profiles.getUsername(), loginDTO.getName())) {
                String hashedPassword = PasswordHash.hashing(loginDTO.getPassword());
                if (Objects.equals(profiles.getPassword(), hashedPassword)) {
                    if (profiles.getRoles().getIdRole() == RolesRepository.get().get(0).getIdRole()) {
                        SceneChanger.change(event, "admin.fxml");LogManager.shutdown();
                        System.setProperty("logFilename", "info.log");
                        Logger logger = LogManager.getLogger();
                        logger.info(profiles.getUsername() + " logged in! Role:" + profiles.getRoles().getRole());
                        return true;
                    } else if (profiles.getRoles().getIdRole() == RolesRepository.get().get(1).getIdRole()) {
                        Profile.setProfiles(profiles);
                        SceneChanger.change(event, "organiser.fxml");
                        LogManager.shutdown();
                        System.setProperty("logFilename", "info.log");
                        Logger logger = LogManager.getLogger();
                        logger.info(profiles.getUsername() + " logged in! Role:" + profiles.getRoles().getRole());
                        return true;
                    } else {
                        Profile.setProfiles(profiles);
                        SceneChanger.change(event, "distributor.fxml");
                        LogManager.shutdown();
                        System.setProperty("logFilename", "info.log");
                        Logger logger = LogManager.getLogger();
                        logger.info(profiles.getUsername() + " logged in! Role:" + profiles.getRoles().getRole());
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }
}

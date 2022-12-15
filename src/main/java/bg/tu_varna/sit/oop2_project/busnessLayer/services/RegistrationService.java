package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.PasswordHash;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.RegistrationDTO;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.*;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Distributor;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Organiser;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Roles;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistrationService {
    private static List<Profiles> profiles;
    public static void init(ChoiceBox box){
        profiles = ProfilesRepository.get();
        List<String> roles=new ArrayList<>();
        for(Roles role: RolesRepository.get()){
            roles.add(role.getRole());
        }
        box.getItems().add(roles.get(1));
        box.getItems().add(roles.get(2));
    }

    public static boolean register(RegistrationDTO registrationDTO) throws NoSuchAlgorithmException {
        boolean flag = true;
        for (Profiles profiles : profiles) {
            if (Objects.equals(profiles.getUsername(),registrationDTO.getUsername())) {
                flag = false;
                break;
            }
        }
        String hashedPassword = PasswordHash.hashing(registrationDTO.getPass());
        if (flag) {
            for (Roles roles : RolesRepository.get()) {
                if (Objects.equals(registrationDTO.getRole(), roles.getRole())) {

                    int id = ProfilesRepository.autonumber();

                    Profiles profiles = new Profiles(id, registrationDTO.getUsername(), hashedPassword, roles);
                    ProfilesRepository.add(profiles);

                    if (Objects.equals(roles.getRole(), "организатор")) {
                        Organiser organiser = new Organiser(profiles, registrationDTO.getFirstName(), registrationDTO.getLastName(), registrationDTO.getEmail(), registrationDTO.getPhone());
                        OrganiserRepository.add(organiser);
                    } else {
                        Distributor distributor = new Distributor(profiles, registrationDTO.getFirstName(), registrationDTO.getLastName(), registrationDTO.getEmail(), registrationDTO.getPhone(), 0, 0);
                        DistributorRepository.add(distributor);
                    }
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

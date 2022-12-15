package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Organiser;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrganiserRepository {

    public static List<Organiser> get(){
        List<Profiles> profiles = ProfilesRepository.get();
        List<Organiser> organisers = new ArrayList<>();
        try {
            ResultSet result = Repository.selectAll("ORGANISER");
            while (result.next()) {
                for (Profiles profile : profiles) {
                    if (profile.getIdProfile() == Integer.parseInt(result.getString(1))) {
                        Organiser organiser = new Organiser(profile, result.getString(2), result.getString(3), result.getString(4), result.getString(5));
                        organisers.add(organiser);
                        break;
                    }
                }
            }
            result.getStatement().close();
        }catch (SQLException e){
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
        return organisers;
    }

    public static void remove(int id) throws SQLException {
        ResultSet result = Repository.remove("ORGANISER","ID_PROFILE",id);
        result.getStatement().close();
    }

    public static void add(Organiser organiser){
        try {
            Connection connection= Database.connection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ORGANISER(ID_PROFILE,FIRSTNAME,LASTNAME,EMAIL,PHONE) VALUES (" + organiser.getIdProfile() + ",'" + organiser.getFirstName() + "','" + organiser.getLastName() + "','" + organiser.getEmail() + "','" + organiser.getPhoneNumber() + "')");
            statement.executeQuery();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void updateFirstName(String value, int id){
        try {
            ResultSet result = Repository.update("ORGANISER","FIRSTNAME",value,"ID_PROFILE",id);
            result.getStatement().close();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void updateLastName(String value, int id){
        try {
            ResultSet result = Repository.update("ORGANISER","LASTNAME",value,"ID_PROFILE",id);
            result.getStatement().close();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void updateEmail(String value, int id){
        try {
            ResultSet result = Repository.update("ORGANISER","EMAIL",value,"ID_PROFILE",id);
            result.getStatement().close();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void updatePhone(String value, int id){
        try {
            ResultSet result = Repository.update("ORGANISER","PHONE",value,"ID_PROFILE",id);
            result.getStatement().close();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
}

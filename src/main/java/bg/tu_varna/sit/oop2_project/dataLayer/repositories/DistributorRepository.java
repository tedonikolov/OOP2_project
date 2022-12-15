package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Distributor;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistributorRepository {
    public static List<Distributor> get(){
        List<Profiles> profiles = ProfilesRepository.get();
        List<Distributor> distributors = new ArrayList<>();
        try {
            ResultSet result = Repository.selectAll("DISTRIBUTOR");
            while (result.next()) {
                for (Profiles profile : profiles) {
                    if (profile.getIdProfile() == Integer.parseInt(result.getString(1))) {
                        Distributor distributor = new Distributor(profile, result.getString(2), result.getString(3), result.getString(4), result.getString(5), Double.parseDouble(result.getString(6)), Double.parseDouble(result.getString(7)));
                        distributors.add(distributor);
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
        return distributors;
    }

    public static void remove(int id) throws SQLException {
        ResultSet result = Repository.remove("DISTRIBUTOR","ID_PROFILE",id);
        result.getStatement().close();
    }

    public static void add(Distributor distributor){
        try {
            Connection connection= Database.connection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO DISTRIBUTOR(ID_PROFILE,FIRSTNAME,LASTNAME,EMAIL,PHONE,RATING,SALARY) VALUES (" + distributor.getIdProfile() + ",'" + distributor.getFirstName() + "','" + distributor.getLastName() + "','" + distributor.getEmail() + "','" + distributor.getPhoneNumber() + "'," + distributor.getRating() + "," + distributor.getSalary() + ")");
            statement.executeQuery();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void updateRating(String value, int id){
        try {
            ResultSet result = Repository.update("DISTRIBUTOR","RATING",value,"ID_PROFILE",id);
            result.getStatement().close();
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
            ResultSet result = Repository.update("DISTRIBUTOR","FIRSTNAME",value,"ID_PROFILE",id);
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
            ResultSet result = Repository.update("DISTRIBUTOR","LASTNAME",value,"ID_PROFILE",id);
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
            ResultSet result = Repository.update("DISTRIBUTOR","EMAIL",value,"ID_PROFILE",id);
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
            ResultSet result = Repository.update("DISTRIBUTOR","PHONE",value,"ID_PROFILE",id);
            result.getStatement().close();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void updateSalary(String value, int id){
        try {
            ResultSet result = Repository.update("DISTRIBUTOR","SALARY",value,"ID_PROFILE",id);
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

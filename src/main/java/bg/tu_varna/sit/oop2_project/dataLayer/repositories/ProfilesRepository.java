package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Roles;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfilesRepository {
    private static List<Roles> roles = RolesRepository.get();

    public static List<Profiles> get(){
        List<Profiles> profiles = new ArrayList<>();
        try {
            ResultSet result = Repository.selectAll("PROFILES");
            while (result.next()) {
                for (Roles role : roles) {
                    if (role.getIdRole() == Integer.parseInt(result.getString(4))) {
                        Profiles profile = new Profiles(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), role);
                        profiles.add(profile);
                        break;
                    }
                }
            }
            result.getStatement().close();
        }
        catch (SQLException e){
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
        return profiles;
    }

    public static void remove(int id) throws SQLException {
        ResultSet result = Repository.remove("PROFILES","ID_PROFILE",id);
        result.getStatement().close();
    }

    public static void updatePassword(String value, int id){
        try {
            ResultSet result = Repository.update("PROFILES","PASSWORD",value,"ID_PROFILE",id);
            result.getStatement().close();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static int autonumber(){
        try {
            ResultSet result = Repository.autonumber("PROFILES_SEQUENCE");
            int id = 0;
            if (result.next())
                id = result.getInt(1);
            result.getStatement().close();
            return id;
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void add(Profiles profiles){
        try {
            Connection connection= Database.connection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO PROFILES(ID_PROFILE,USERNAME,PASSWORD,ROLE_ID) VALUES (" + profiles.getIdProfile() + ",'" + profiles.getUsername() + "','" + profiles.getPassword() + "'," + profiles.getRoles().getIdRole() + ")");
            statement.executeQuery();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
}

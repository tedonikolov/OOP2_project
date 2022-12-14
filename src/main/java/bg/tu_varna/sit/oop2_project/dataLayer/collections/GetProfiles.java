package bg.tu_varna.sit.oop2_project.dataLayer.collections;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Roles;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetProfiles {
    private static List<Roles> roles = GetRoles.get();

    public static List<Profiles> get(){
        List<Profiles> profiles = new ArrayList<>();
        try {
            ResultSet result = SelectAll.selectAll("PROFILES");
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
}

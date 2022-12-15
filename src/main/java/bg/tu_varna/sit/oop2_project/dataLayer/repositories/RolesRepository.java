package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.entities.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolesRepository {

    private static List<Roles> roles=null;
    public static List<Roles> get(){
        if(roles==null) {
            roles = new ArrayList<>();
            try {
                ResultSet result = Repository.selectAll("ROLES");
                while (result.next()) {
                    Roles role = new Roles(Integer.parseInt(result.getString(1)), result.getString(2));
                    roles.add(role);
                }
                result.getStatement().close();
            } catch (SQLException e) {
                LogManager.shutdown();
                System.setProperty("logFilename", "fatal.log");
                Logger logger = LogManager.getLogger();
                logger.fatal(e);
                throw new RuntimeException(e);
            }
        }
        return roles;
    }
}

package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetRoles {

    private static List<Roles> roles=null;
    public static List<Roles> get(){
        if(roles==null) {
            roles = new ArrayList<>();
            try {
                ResultSet result = SelectAll.selectAll("ROLES");
                while (result.next()) {
                    Roles role = new Roles(Integer.parseInt(result.getString(1)), result.getString(2));
                    roles.add(role);
                }
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

package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Distributor;
import bg.tu_varna.sit.oop2_project.entities.Profiles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetDistributors {
    private static List<Profiles> profiles = GetProfiles.get();
    public static List<Distributor> get(){
        List<Distributor> distributors = new ArrayList<>();
        try {
            ResultSet result = SelectAll.selectAll("DISTRIBUTOR");
            while (result.next()) {
                for (Profiles profile : profiles) {
                    if (profile.getIdProfile() == Integer.parseInt(result.getString(1))) {
                        Distributor distributor = new Distributor(profile, result.getString(2), result.getString(3), result.getString(4), result.getString(5), Double.parseDouble(result.getString(6)), Double.parseDouble(result.getString(7)));
                        distributors.add(distributor);
                        break;
                    }
                }
            }
        }catch (SQLException e){
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
        return distributors;
    }
}

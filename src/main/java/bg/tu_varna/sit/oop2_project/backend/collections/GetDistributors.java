package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Distributor;
import bg.tu_varna.sit.oop2_project.entities.Profiles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetDistributors {

    public static List<Distributor> get() throws SQLException {
        List<Profiles> profiles = GetProfiles.get();
        ResultSet result = SelectAll.selectAll("DISTRIBUTOR");
        List<Distributor> distributors = new ArrayList<>();
        while (result.next()) {
            for (Profiles profile : profiles) {
                if (profile.getIdProfile() == Integer.parseInt(result.getString(1))) {
                    Distributor distributor = new Distributor(profile,result.getString(2), result.getString(3), result.getString(4), result.getString(5), Double.parseDouble(result.getString(6)), Double.parseDouble(result.getString(7)));
                    distributors.add(distributor);
                    break;
                }
            }
        }
        return distributors;
    }
}

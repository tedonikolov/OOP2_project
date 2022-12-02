package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Organiser;
import bg.tu_varna.sit.oop2_project.entities.Profiles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetOrganisers {

    public static List<Organiser> get() throws SQLException {
        List<Profiles> profiles = GetProfiles.get();
        ResultSet result = SelectAll.selectAll("ORGANISER");
        List<Organiser> organisers = new ArrayList<>();
        while (result.next()) {
            for(Profiles profile : profiles){
                if(profile.getIdProfile()==Integer.parseInt(result.getString(1))){
                    Organiser organiser= new Organiser(profile,result.getString(2),result.getString(3),result.getString(4),result.getString(5));
                    organisers.add(organiser);
                    break;
                }
            }

        }
        return organisers;
    }
}

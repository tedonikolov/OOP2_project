package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Roles;
import bg.tu_varna.sit.oop2_project.entities.Profiles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetProfiles {

    public static List<Profiles> get() throws SQLException {
        List<Roles> roles = GetRoles.get();
        ResultSet result = SelectAll.selectAll("PROFILES");
        List<Profiles> profiles = new ArrayList<>();
        while (result.next()) {
            for(Roles role:roles){
                if(role.getIdRole()==Integer.parseInt(result.getString(4))){
                    Profiles profile= new Profiles(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3),role);
                    profiles.add(profile);
                    break;
                }
            }
        }
        return profiles;
    }
}

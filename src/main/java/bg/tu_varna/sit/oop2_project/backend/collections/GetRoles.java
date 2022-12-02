package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Roles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetRoles {

    public static List<Roles> get() throws SQLException {
        ResultSet result = SelectAll.selectAll("ROLES");
        List<Roles> roles = new ArrayList<>();
        while (result.next()) {
            Roles role = new Roles(Integer.parseInt(result.getString(1)), result.getString(2));
            roles.add(role);
        }
        return roles;
    }
}

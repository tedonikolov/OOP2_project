package entity;

import bg.tu_varna.sit.oop2_project.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetRoles {
    private static List<Role> roles=null;

    public static List<Role> getRoles() throws SQLException {
        if(roles==null) {
            roles=new ArrayList<>();
            Connection connection = Database.connection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ROLES ");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Role role = new Role(Integer.parseInt(result.getString(1)), result.getString(2));
                roles.add(role);
            }
        }
        return roles;
    }
}

package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.backend.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectAll {

    public static ResultSet selectAll(String table) throws SQLException {
        Connection connection = Database.connection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM "+table);
        return statement.executeQuery();
    }
}

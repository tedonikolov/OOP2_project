package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Repository {
    private static Connection connection = Database.connection();

    public static ResultSet selectAll(String table) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM "+table);
        return statement.executeQuery();
    }

    public static ResultSet remove(String table, String column, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM "+table+" WHERE " +column+" = "+id);
        return statement.executeQuery();
    }

    public static ResultSet update(String table, String column, String information, String column2, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE "+table+" SET "+column+" = '"+information +"' WHERE " +column2+" = "+id);
        return statement.executeQuery();
    }

    public static ResultSet autonumber(String sequence) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT "+sequence+".nextVal from DUAL");
        return statement.executeQuery();
    }
}

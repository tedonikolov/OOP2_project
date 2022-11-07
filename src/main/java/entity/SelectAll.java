package entity;

import bg.tu_varna.sit.oop2_project.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectAll {

    public static List<?> getAll(String table) throws SQLException {

        List<?> list = new ArrayList<>();
        Connection connection = Database.connection();
        switch (table){
            case "ROLES":
            {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM ROLES");
                ResultSet result = statement.executeQuery();
                List<Roles> roles = new ArrayList<>();
                while (result.next()) {
                    Roles role = new Roles(Integer.parseInt(result.getString(1)), result.getString(2));
                    roles.add(role);
                }
                list=roles;
                break;
            }
            case "PROFILES":
            {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM PROFILES JOIN ROLES ON ROLES.ID_ROLE=PROFILES.ROLE_ID");
                ResultSet result = statement.executeQuery();
                List<Profiles> profiles = new ArrayList<>();
                while (result.next()) {
                    Profiles profile= new Profiles(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3),new Roles(Integer.parseInt(result.getString(5)), result.getString(6)));
                    profiles.add(profile);
                }
                list=profiles;
                break;
            }
            case "DISTRIBUTOR":
            {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM DISTRIBUTOR JOIN PROFILES ON PROFILES.ID_PROFILE=DISTRIBUTOR.ID_PROFILE JOIN ROLES ON ROLES.ID_ROLE=PROFILES.ROLE_ID");
                ResultSet result = statement.executeQuery();
                List<Distributor> distributors = new ArrayList<>();
                while (result.next()) {
                    Distributor distributor= new Distributor(new Profiles(Integer.parseInt(result.getString(8)), result.getString(9), result.getString(10),new Roles(Integer.parseInt(result.getString(11)), result.getString(13))),result.getString(2),result.getString(3),result.getString(4),result.getString(5),Double.parseDouble(result.getString(6)),Double.parseDouble(result.getString(7)));
                    distributors.add(distributor);
                }
                list=distributors;
                break;
            }
            case "ORGANIZER":
            {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORGANISER JOIN PROFILES ON PROFILES.ID_PROFILE=DISTRIBUTOR.ID_PROFILE JOIN ROLES ON ROLES.ID_ROLE=PROFILES.ROLE_ID");
                ResultSet result = statement.executeQuery();
                List<Organiser> organisers = new ArrayList<>();
                while (result.next()) {
                    Organiser organiser= new Organiser(new Profiles(Integer.parseInt(result.getString(8)), result.getString(9), result.getString(10),new Roles(Integer.parseInt(result.getString(11)), result.getString(13))),result.getString(2),result.getString(3),result.getString(4),result.getString(5));
                    organisers.add(organiser);
                }
                list=organisers;
                break;
            }
        }
        Database.close();
        return list;
    }
}

package bg.tu_varna.sit.oop2_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection=null;

    public static Connection connection() {
        if (connection == null) {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "ADMIN";
            String password = "exit";
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println("can't connect to the database");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void close() throws SQLException {
        connection.close();
        connection=null;
    }
}

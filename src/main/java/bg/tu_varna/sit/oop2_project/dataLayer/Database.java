package bg.tu_varna.sit.oop2_project.dataLayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
                LogManager.shutdown();
                System.setProperty("logFilename", "info.log");
                Logger logger = LogManager.getLogger();
                logger.info("Connected to database");
            } catch (SQLException e) {
                LogManager.shutdown();
                System.setProperty("logFilename", "fatal.log");
                Logger logger = LogManager.getLogger();
                logger.fatal("Can't connect to the database");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void close(){
        try {
            connection.close();
            connection=null;
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal("Database is close");
            e.printStackTrace();
        }
    }
}

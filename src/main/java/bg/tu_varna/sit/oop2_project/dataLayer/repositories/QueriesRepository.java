package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class QueriesRepository {
    public static PreparedStatement selectEventAndDistributor(String event, int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, TICKETSOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID JOIN TICKETS T on SECTORS.ID_SECTORS = T.SECTORS_ID WHERE NAME='" + event + "' AND DISTRIBUTOR_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
    public static PreparedStatement selectByDateAndDistributor(LocalDateTime fromDate, LocalDateTime toDate, int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, TICKETSOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID JOIN TICKETS T on SECTORS.ID_SECTORS = T.SECTORS_ID WHERE DATETIME BETWEEN to_date('" + fromDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND to_date('" + toDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND DISTRIBUTOR_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
    public static PreparedStatement selectByDateAndAddressAndDistributor(LocalDateTime fromDate,LocalDateTime toDate, String address, int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, TICKETSOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID JOIN TICKETS T on SECTORS.ID_SECTORS = T.SECTORS_ID WHERE DATETIME BETWEEN to_date('" + fromDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND to_date('" + toDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND ADDRESS LIKE '%" + address + "%' AND DISTRIBUTOR_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
    public static PreparedStatement selectByAddressAndDistributor(String address, int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, TICKETSOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID JOIN TICKETS T on SECTORS.ID_SECTORS = T.SECTORS_ID WHERE ADDRESS LIKE '%" + address + "%' AND DISTRIBUTOR_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectAllAndDistributor(int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, TICKETSOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID JOIN TICKETS T on SECTORS.ID_SECTORS = T.SECTORS_ID WHERE DISTRIBUTOR_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
}

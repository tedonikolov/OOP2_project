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

    public static PreparedStatement selectAllByDistributor(int id){
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
    public static PreparedStatement selectEventAndOrganiser(String event, int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, SOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID WHERE NAME='" + event + "' AND ORGANISER_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectByDateAndOrganiser(LocalDateTime fromDate, LocalDateTime toDate, int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, SOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID WHERE DATETIME BETWEEN to_date('" + fromDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND to_date('" + toDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND ORGANISER_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectByDateAndAddressAndOrganiser(LocalDateTime fromDate,LocalDateTime toDate, String address, int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, SOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID WHERE DATETIME BETWEEN to_date('" + fromDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND to_date('" + toDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND ADDRESS LIKE '%" + address + "%' AND ORGANISER_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectByAddressAndOrganiser(String address, int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, SOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID WHERE ADDRESS LIKE '%" + address + "%' AND ORGANISER_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectAllByOrganiser(int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, SOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID WHERE ORGANISER_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectDistributorsBetweenSoldTickets(int from,int to,int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD BETWEEN " + from + " AND " + to + " AND ORGANISER_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectDistributorsMoreThenSoldTickets(int from,int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD>=" + from + " AND ORGANISER_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectDistributorsLessThenSoldTickets(int to,int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD<=" + to + " AND ORGANISER_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectDistributors(int id){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE ORGANISER_ID=" + id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectDistributorsBetweenSoldTicketsAndEvent(int from,int to,int id,String event){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD BETWEEN "+from+" AND "+to+" AND NAME='"+event+"' AND ORGANISER_ID="+id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectDistributorsMoreThenSoldTicketsAndEvent(int from,int id,String event){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD>="+from+" AND NAME='"+event+"' AND ORGANISER_ID="+id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectDistributorsLessThenSoldTicketsAndEvent(int to,int id,String event){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD<="+to+" AND NAME='"+event+"' AND ORGANISER_ID="+id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement selectDistributorsByEvent(int id,String event){
        try {
            Connection connection = Database.connection();
            return connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE NAME='"+event+"' AND ORGANISER_ID="+id);
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
}

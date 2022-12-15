package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Seats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatsRepository {
    public static List<Seats> get(){
        List<Seats> seats = new ArrayList<>();
        try {
            ResultSet result = Repository.selectAll("SEATS");
            while (result.next()) {
                Seats seat = new Seats(Integer.parseInt(result.getString(1)), result.getString(2), Integer.parseInt(result.getString(3)), Integer.parseInt(result.getString(4)), Double.parseDouble(result.getString(5)), Integer.parseInt(result.getString(6)));
                seats.add(seat);
            }
            result.getStatement().close();
        }catch (SQLException e){
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
        return seats;
    }

    public static int autonumber(){
        try {
            ResultSet result = Repository.autonumber("SEATS_SEQUENCE");
            int id = 0;
            if (result.next())
                id = result.getInt(1);
            result.getStatement().close();
            return id;
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void add(Seats seats){
        try {
            Connection connection= Database.connection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO SEATS(ID_SEATS, TYPE, AMOUNT, SOLD, PRICE, TICKETPERCLIENT) VALUES ("+seats.getIdSeats()+",'"+seats.getType()+"',"+seats.getAmount()+","+seats.getSold()+","+seats.getPrice()+","+seats.getTicketPerClient()+")");
            statement.executeQuery();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void updateAmount(String value, int id){
        try {
            ResultSet result = Repository.update("SEATS","AMOUNT",value,"ID_SEATS",id);
            result.getStatement().close();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void updateSold(String value, int id){
        try {
            ResultSet result = Repository.update("SEATS","SOLD",value,"ID_SEATS",id);
            result.getStatement().close();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
}

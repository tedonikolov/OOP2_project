package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Seats;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectorsRepository {
    public static List<Sectors> get(){
        List<Event> events = EventRepository.get();
        List<Seats> seats = SeatsRepository.get();
        List<Sectors> sectors = new ArrayList<>();
        try {

            ResultSet result = Repository.selectAll("SECTORS");
            while (result.next()) {
                for (Event event : events) {
                    if (event.getIdEvent() == Integer.parseInt(result.getString(2))) {
                        for (Seats seat : seats) {
                            if (seat.getIdSeats() == Integer.parseInt(result.getString(3))) {
                                Sectors sector = new Sectors(Integer.parseInt(result.getString(1)), event, seat);
                                sectors.add(sector);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            result.getStatement().close();
        }catch (SQLException e){
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
        return sectors;
    }

    public static int autonumber(){
        try {
            ResultSet result = Repository.autonumber("SECTORS_SEQUENCE");
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

    public static void add(Sectors sectors){
        try {
            Connection connection= Database.connection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO SECTORS (ID_SECTORS, EVENT_ID, SEATS_ID) VALUES ("+sectors.getIdSectors()+","+sectors.getEvent().getIdEvent()+","+sectors.getSeats().getIdSeats()+")");
            statement.executeQuery();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
}

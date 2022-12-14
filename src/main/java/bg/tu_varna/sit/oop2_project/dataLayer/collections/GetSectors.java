package bg.tu_varna.sit.oop2_project.dataLayer.collections;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Seats;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetSectors {
    public static List<Sectors> get(){
        List<Event> events = GetEvents.get();
        List<Seats> seats = GetSeats.get();
        List<Sectors> sectors = new ArrayList<>();
        try {

            ResultSet result = SelectAll.selectAll("SECTORS");
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
}

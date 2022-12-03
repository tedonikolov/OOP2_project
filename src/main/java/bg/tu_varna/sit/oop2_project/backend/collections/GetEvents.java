package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Event;
import bg.tu_varna.sit.oop2_project.entities.Organiser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GetEvents {
    public static List<Event> get(){
        List<Event> events = new ArrayList<>();
        try {
            List<Organiser> organisers = GetOrganisers.get();
            ResultSet result = SelectAll.selectAll("EVENT");
            while (result.next()) {
                for (Organiser organiser : organisers) {
                    if (organiser.getIdProfile() == Integer.parseInt(result.getString(6))) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        Event event = new Event(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), LocalDateTime.parse(result.getString(4), formatter), result.getString(5), organiser);
                        events.add(event);
                        break;
                    }
                }
            }
        }catch (SQLException e){
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
        return events;
    }
}

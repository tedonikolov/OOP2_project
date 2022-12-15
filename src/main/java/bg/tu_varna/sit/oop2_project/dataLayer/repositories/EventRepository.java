package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Organiser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    public static List<Event> get(){
        List<Event> events = new ArrayList<>();
        List<Organiser> organisers = OrganiserRepository.get();
        try {
            ResultSet result = Repository.selectAll("EVENT");
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
            result.getStatement().close();
        }catch (SQLException e){
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
        return events;
    }

    public static int autonumber(){
        try {
            ResultSet result = Repository.autonumber("EVENT_SEQUENCE");
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

    public static void add(Event event){
        try {
            Connection connection= Database.connection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO EVENT(ID_EVENT,NAME,ADDRESS,DATETIME,DESCRIPTION,ORGANISER_ID) VALUES ("+event.getIdEvent()+",'" + event.getName() + "','" + event.getAddress() + "',to_date('" + event.getDateTime() + "','YYYY-MM-DD\"T\"HH24:MI:SS'),'" + event.getDescription() + "'," + event.getOrganiser().getIdProfile() + ")");
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

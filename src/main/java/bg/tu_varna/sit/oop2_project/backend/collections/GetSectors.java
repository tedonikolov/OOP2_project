package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Event;
import bg.tu_varna.sit.oop2_project.entities.Seats;
import bg.tu_varna.sit.oop2_project.entities.Sectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetSectors {


    public static List<Sectors> get() throws SQLException {
        List<Event> events = GetEvents.get();
        List<Seats> seats = GetSeats.get();

        ResultSet result = SelectAll.selectAll("SECTORS");
        List<Sectors> sectors = new ArrayList<>();
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
        return sectors;
    }
}

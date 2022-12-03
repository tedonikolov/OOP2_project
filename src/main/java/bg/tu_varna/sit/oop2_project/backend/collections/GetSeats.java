package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Seats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetSeats {
    public static List<Seats> get() throws SQLException {
        ResultSet result = SelectAll.selectAll("SEATS");
        List<Seats> seats = new ArrayList<>();
        while (result.next()) {
            Seats seat = new Seats(Integer.parseInt(result.getString(1)),result.getString(2),Integer.parseInt(result.getString(3)),Integer.parseInt(result.getString(4)),Double.parseDouble(result.getString(5)),Integer.parseInt(result.getString(6)));
            seats.add(seat);
        }
        return seats;
    }
}

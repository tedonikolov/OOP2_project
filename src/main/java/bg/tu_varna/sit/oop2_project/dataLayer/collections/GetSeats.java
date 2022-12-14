package bg.tu_varna.sit.oop2_project.dataLayer.collections;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Seats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetSeats {
    public static List<Seats> get(){
        List<Seats> seats = new ArrayList<>();
        try {
            ResultSet result = SelectAll.selectAll("SEATS");
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
}

package bg.tu_varna.sit.oop2_project.dataLayer.collections;

import bg.tu_varna.sit.oop2_project.dataLayer.entities.Distributor;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Tickets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class GetTickets {
    public static List<Tickets> get(){
        List<Distributor> distributors = GetDistributors.get();
        List<Sectors> sectors = GetSectors.get();
        List<Tickets> tickets = new ArrayList<>();
        try {
            ResultSet result = SelectAll.selectAll("TICKETS");

            while (result.next()) {
                for (Sectors sector : sectors) {
                    if (sector.getIdSectors() == Integer.parseInt(result.getString(2))) {
                        for (Distributor distributor : distributors) {
                            if (distributor.getIdProfile() == Integer.parseInt(result.getString(4))) {
                                Tickets ticket = new Tickets(Integer.parseInt(result.getString(1)), sector, Integer.parseInt(result.getString(3)), distributor, Integer.parseInt(result.getString(5)));
                                tickets.add(ticket);
                                break;
                            }
                        }
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
        return tickets;
    }
}

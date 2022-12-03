package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Distributor;
import bg.tu_varna.sit.oop2_project.entities.Sectors;
import bg.tu_varna.sit.oop2_project.entities.Tickets;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class GetTickets {

    public static List<Tickets> get() throws SQLException {
        ResultSet result = SelectAll.selectAll("TICKETS");
        List<Sectors> sectors = GetSectors.get();
        List<Distributor> distributors = GetDistributors.get();

        List<Tickets> tickets = new ArrayList<>();
        while (result.next()) {
            for (Sectors sector : sectors) {
                if (sector.getIdSectors() == Integer.parseInt(result.getString(2))) {
                    for (Distributor distributor : distributors) {
                        if (distributor.getIdProfile() == Integer.parseInt(result.getString(4))) {
                            Tickets ticket = new Tickets(Integer.parseInt(result.getString(1)), sector, Integer.parseInt(result.getString(3)), distributor,Integer.parseInt(result.getString(5)));
                            tickets.add(ticket);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return tickets;
    }
}

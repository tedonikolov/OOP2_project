package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Client;
import bg.tu_varna.sit.oop2_project.entities.Tickets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetClients {
    public static List<Client> get() throws SQLException {
        List<Tickets> tickets=GetTickets.get();
        ResultSet result = SelectAll.selectAll("CLIENT");
        List<Client> clients = new ArrayList<>();
        while (result.next()) {
            for(Tickets ticket:tickets) {
                if (ticket.getIdTicket() == Integer.parseInt(result.getString(6))) {
                    Client client = new Client(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), result.getString(5), ticket, Integer.parseInt(result.getString(7)));
                    clients.add(client);
                    break;
                }
            }
        }
        return clients;
    }
}

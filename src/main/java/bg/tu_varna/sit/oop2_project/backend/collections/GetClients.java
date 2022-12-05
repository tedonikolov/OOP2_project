package bg.tu_varna.sit.oop2_project.backend.collections;

import bg.tu_varna.sit.oop2_project.entities.Client;
import bg.tu_varna.sit.oop2_project.entities.Tickets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetClients {
    private static List<Tickets> tickets = GetTickets.get();
    public static List<Client> get(){
        List<Client> clients = new ArrayList<>();
        try {
            ResultSet result = SelectAll.selectAll("CLIENT");
            while (result.next()) {
                for (Tickets ticket : tickets) {
                    if (ticket.getIdTicket() == Integer.parseInt(result.getString(6))) {
                        Client client = new Client(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), result.getString(5), ticket, Integer.parseInt(result.getString(7)));
                        clients.add(client);
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
        return clients;
    }
}

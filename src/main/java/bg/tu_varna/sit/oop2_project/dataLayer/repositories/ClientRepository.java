package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Client;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Tickets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    public static List<Client> get(){
        List<Tickets> tickets = TicketsRepository.get();
        List<Client> clients = new ArrayList<>();
        try {
            ResultSet result = Repository.selectAll("CLIENT");
            while (result.next()) {
                for (Tickets ticket : tickets) {
                    if (ticket.getIdTicket() == Integer.parseInt(result.getString(6))) {
                        Client client = new Client(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), result.getString(5), ticket, Integer.parseInt(result.getString(7)));
                        clients.add(client);
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
        return clients;
    }

    public static int autonumber(){
        try {
            ResultSet result = Repository.autonumber("CLIENT_SEQUENCE");
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

    public static void add(Client client){
        try {
            Connection connection= Database.connection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENT(ID_CLIENT,FIRSTNAME, LASTNAME, EMAIL, PHONE, TICKET_ID, QUANTITY) VALUES ("+client.getIdClient()+",'"+client.getFirstName()+"','"+client.getLastName()+"','"+client.getEmail()+"','"+client.getPhoneNumber()+"',"+client.getTicket().getIdTicket()+","+client.getQuantity()+")");
            statement.executeQuery();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void update(String value, int id){
        try {
            ResultSet result = Repository.update("CLIENT","QUANTITY",value,"ID_CLIENT",id);
            result.getStatement().close();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
}

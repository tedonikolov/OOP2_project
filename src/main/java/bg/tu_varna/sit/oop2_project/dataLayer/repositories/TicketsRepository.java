package bg.tu_varna.sit.oop2_project.dataLayer.repositories;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Distributor;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Tickets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class TicketsRepository {
    public static List<Tickets> get(){
        List<Distributor> distributors = DistributorRepository.get();
        List<Sectors> sectors = SectorsRepository.get();
        List<Tickets> tickets = new ArrayList<>();
        try {
            ResultSet result = Repository.selectAll("TICKETS");

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
            result.getStatement().close();
        }catch (SQLException e){
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public static int autonumber(){
        try {
            ResultSet result = Repository.autonumber("TICKETS_SEQUENCE");
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

    public static void add(Tickets ticket){
        try {
            Connection connection= Database.connection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO TICKETS(ID_TICKET, SECTORS_ID, TICKETSOLD, DISTRIBUTOR_ID, RATE) VALUES (" + ticket.getIdTicket() + "," + ticket.getSectors().getIdSectors() + "," + ticket.getTicketsSold() + "," + ticket.getDistributor().getIdProfile() + "," + ticket.getRate() + ")");
            statement.executeQuery();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void updateRate(String value, int id){
        try {
            ResultSet result = Repository.update("TICKETS","RATE",value,"DISTRIBUTOR_ID",id);
            result.getStatement().close();
        } catch (SQLException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static void updateTicketSold(String value, int id){
        try {
            ResultSet result = Repository.update("TICKETS","TICKETSOLD",value,"ID_TICKET",id);
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

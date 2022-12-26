package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.*;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class PurchaseService {
    private static Tickets ticket;
    private static Sectors sector;
    private static Seats seats;
    public static void init(ComboBox eventBox){
        Set<String> events=new HashSet<>();
        List<Tickets> ticketsList = TicketsRepository.get();
        for(Tickets tickets: ticketsList) {
            if (tickets.getDistributor().getIdProfile() == Profile.getProfiles().getIdProfile())
                events.add(tickets.getSectors().getEvent().getName());
        }
        eventBox.getItems().addAll(events);
    }

    public static void seats(String event,ComboBox seatsBox){
        List<Sectors>sectorsList = SectorsRepository.get();
        List<String> list = new ArrayList<>();
        for (Event event1 : EventRepository.get()) {
            if (Objects.equals(event1.getName(), event)) {
                for (Sectors sectors : sectorsList) {
                    if (sectors.getEvent().getIdEvent() == event1.getIdEvent())
                        list.add(sectors.getSeats().getType());
                }
            }
        }
        seatsBox.getItems().addAll(list);
    }

    public static void sectors(String event, String seat, Label price){
        List<Sectors> sectorsList = SectorsRepository.get();
        List<Tickets> ticketsList = TicketsRepository.get();
        for (Sectors sectors : sectorsList) {
            if (Objects.equals(sectors.getEvent().getName(), event) && Objects.equals(sectors.getSeats().getType(), seat)) {
                for (Tickets tickets : ticketsList) {
                    if (tickets.getDistributor().getIdProfile() == Profile.getProfiles().getIdProfile() && Objects.equals(sectors.getSeats().getIdSeats(), tickets.getSectors().getSeats().getIdSeats())) {
                        ticket = tickets;
                        sector = sectors;
                        seats = sectors.getSeats();
                        price.setText("Цена на един билет (лв): " + tickets.getSectors().getSeats().getPrice());
                        break;
                    }
                }
            }
        }
    }

    public static void buy(String name,String lastName,String email,String phone,int amount,Label left,Label error){
        if(seats.getAmount()>amount) {
            List<Client> clients = ClientRepository.get();
            boolean flag = true;
            for (Client client : clients) {
                if (Objects.equals(client.getEmail(),email) && sector.getIdSectors() == client.getTicket().getSectors().getIdSectors()) {
                    if ((client.getQuantity() + amount) > seats.getTicketPerClient()) {
                        error.setVisible(true);
                        error.setText("Надвишавате лимита за купуване на билети");
                        left.setText("За момента имате " + client.getQuantity() + " броя купени билети. Може да купите още " + (seats.getTicketPerClient() - client.getQuantity()));
                        left.setVisible(true);
                    } else {
                        ClientRepository.update(String.valueOf((client.getQuantity() + amount)), client.getIdClient());

                        seats.setAmount(seats.getAmount() - amount);
                        SeatsRepository.updateAmount(String.valueOf(seats.getAmount()), seats.getIdSeats());

                        seats.setSold(seats.getSold() + amount);
                        SeatsRepository.updateSold(String.valueOf(seats.getSold()), seats.getIdSeats());

                        ticket.setTicketsSold(ticket.getTicketsSold() + amount);
                        TicketsRepository.updateTicketSold(String.valueOf(ticket.getTicketsSold()), ticket.getIdTicket());

                        left.setText("Покупката е успешна");
                        left.setVisible(true);
                        error.setVisible(false);

                        LogManager.shutdown();
                        System.setProperty("logFilename", "info.log");
                        Logger logger = LogManager.getLogger();
                        logger.info("Client added successful! client ID:" + client.getIdClient());

                        LogManager.shutdown();
                        System.setProperty("logFilename", "organiser_id_" + ticket.getSectors().getEvent().getOrganiser().getIdProfile() + ".log");
                        logger = LogManager.getLogger();
                        logger.info(sector.getEvent().getName() + "," + seats.getType() + "," + amount);
                    }
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (amount > seats.getTicketPerClient()) {
                    error.setVisible(true);
                    error.setText("Надвишавате лимита за купуване на билети");
                    left.setText("Може да купите максимум " + seats.getTicketPerClient() + " билета");
                    left.setVisible(true);
                } else
                    insert(name, lastName, email, phone, amount, left, error);
            }
        }else {
            error.setVisible(true);
            error.setText("Няма достатъчно билети!");
        }
    }

    public static void insert(String name,String lastName,String email,String phone,int amount,Label left,Label error){
        int id=ClientRepository.autonumber();

        Client client=new Client(id,name,lastName,email,phone,ticket,amount);
        ClientRepository.add(client);

        seats.setAmount(seats.getAmount() - amount);
        SeatsRepository.updateAmount(String.valueOf(seats.getAmount()), seats.getIdSeats());

        seats.setSold(seats.getSold() + amount);
        SeatsRepository.updateSold(String.valueOf(seats.getSold()), seats.getIdSeats());

        ticket.setTicketsSold(ticket.getTicketsSold() + amount);
        TicketsRepository.updateTicketSold(String.valueOf(ticket.getTicketsSold()),ticket.getIdTicket());
        left.setText("Покупката е успешна");
        left.setVisible(true);
        error.setVisible(false);

        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("Client added successful! client ID:"+client.getIdClient());

        LogManager.shutdown();
        System.setProperty("logFilename", "organiser_id_"+ticket.getSectors().getEvent().getOrganiser().getIdProfile()+".log");
        logger = LogManager.getLogger();
        logger.info(sector.getEvent().getName()+","+seats.getType()+","+client.getQuantity());
    }
}

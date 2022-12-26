package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Distributor;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Tickets;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.DistributorRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.EventRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.SectorsRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.TicketsRepository;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TicketService {
    public static void addDistributor(String distributorName, String event){
        List<Distributor> distributors = DistributorRepository.get();
        List<Sectors> sectorsList = SectorsRepository.get();

        for (Distributor distributor : distributors) {
            String string = distributorName;
            String[] name = string.split(" ");
            if (Objects.equals(distributor.getFirstName(), name[0]) && Objects.equals(distributor.getFirstName(), name[0])) {
                for (Sectors sectors : sectorsList) {
                    if (Objects.equals(sectors.getEvent().getName(), event)) {

                        int id = TicketsRepository.autonumber();
                        Tickets ticket = new Tickets(id, sectors, 0, distributor, 0);
                        TicketsRepository.add(ticket);

                        LogManager.shutdown();
                        System.setProperty("logFilename", "info.log");
                        Logger logger = LogManager.getLogger();
                        logger.info("Distributor added successful: " + distributor.getIdProfile() + "," + distributor.getFirstName() + "," + distributor.getLastName());

                        LogManager.shutdown();
                        System.setProperty("logFilename", "distributor_id_" + distributor.getIdProfile() + ".log");
                        logger = LogManager.getLogger();
                        logger.info(sectors.getEvent().getName());
                    }
                }
            }
        }
    }

    public static void distributors(ChoiceBox distributor,List<Tickets> tickets, String event){
        distributor.getItems().clear();
        List<String> distributors=new ArrayList<>();

        for(Distributor distributor1: DistributorRepository.get()){
            boolean flag=true;
            for(Tickets tickets1 : tickets){
                if(Objects.equals(tickets1.getSectors().getEvent().getName(), event)&&Objects.equals(tickets1.getDistributor().getIdProfile(), distributor1.getIdProfile())){
                    flag=false;
                    break;
                }
            }
            if (flag) {
                String fullName=distributor1.getFirstName()+" "+ distributor1.getLastName();
                distributors.add(fullName);
            }
        }
        distributor.getItems().addAll(distributors);
    }
    public static void init(ChoiceBox event){
        List<String> events = new ArrayList<>();
        for (Event event1 : EventRepository.get()) {
            if (event1.getOrganiser().getIdProfile() == Profile.getProfiles().getIdProfile())
                events.add(event1.getName());
        }
        event.getItems().addAll(events);
    }
}

package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Seats;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.EventRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.SeatsRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.SectorsRepository;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeatsService {
    public static void create(String event, String type, int amount, Double price, int ticketsPerClient){
        for (Event event1: EventRepository.get()){
            if(event1.getOrganiser().getIdProfile()== Profile.getProfiles().getIdProfile()&& Objects.equals(event1.getName(), event)){
                int id= SeatsRepository.autonumber();
                Seats seats=new Seats(id,type,amount,0,price,ticketsPerClient);
                SeatsRepository.add(seats);

                id = SectorsRepository.autonumber();
                Sectors sectors = new Sectors(id,event1,seats);
                SectorsRepository.add(sectors);
                LogManager.shutdown();
                System.setProperty("logFilename", "info.log");
                Logger logger = LogManager.getLogger();
                logger.info("Seat crated successful: "+seats.getIdSeats()+","+seats.getType());
            }
        }
    }

    public static void init(ChoiceBox event){
        List<String> events=new ArrayList<>();
        for(Event event1: EventRepository.get()){
            if(event1.getOrganiser().getIdProfile()== Profile.getProfiles().getIdProfile())
                events.add(event1.getName());
        }
        event.getItems().addAll(events);
    }
}

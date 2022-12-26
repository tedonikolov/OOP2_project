package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Organiser;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.EventRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.OrganiserRepository;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class EventService {
    public static void create(String name, String address, String time, String description, int year, Month month, int day){
        int id = EventRepository.autonumber();

        String[] arr = time.split(":");
        int hour = Integer.parseInt(arr[0]);
        int minute = Integer.parseInt(arr[1]);
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);

        for (Organiser organiser : OrganiserRepository.get()) {
            if (organiser.getIdProfile() == Profile.getProfiles().getIdProfile()) {
                Event event = new Event(id, name, address, dateTime, description, organiser);
                EventRepository.add(event);
                LogManager.shutdown();
                System.setProperty("logFilename", "info.log");
                Logger logger = LogManager.getLogger();
                logger.info("Event crated successful: " + event.getIdEvent() + "," + event.getName());
            }
        }
    }

    public static void disableDays(DatePicker datePicker){
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }
}

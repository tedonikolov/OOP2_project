package bg.tu_varna.sit.oop2_project.entities;

import java.time.LocalDateTime;

public class Event {
    private int idEvent;
    private String name;
    private String address;
    private LocalDateTime dateTime;
    private String description;
    private Organiser organiser;

    public Event(int idEvent, String name, String address, LocalDateTime dateTime, String description, Organiser organiser) {
        this.idEvent = idEvent;
        this.name = name;
        this.address = address;
        this.dateTime = dateTime;
        this.description = description;
        this.organiser = organiser;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organiser getOrganiser() {
        return organiser;
    }

    public void setOrganiser(Organiser organiser) {
        this.organiser = organiser;
    }
}

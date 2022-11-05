package entity;

import java.util.Date;

public class Event {
    private int idEvent;
    private String name;
    private String address;
    private Date date;
    private String description;
    private Organiser organiser;

    public Event(int idEvent, String name, String address, Date date, String description, Organiser organiser) {
        this.idEvent = idEvent;
        this.name = name;
        this.address = address;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

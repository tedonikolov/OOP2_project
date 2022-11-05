package entity;

import entity.Organiser;

public class Event {
    String name;
    String address;
    String date;
    String description;
    Organiser profileOrganiser;

    public Event(String name, String address, String date, String description, Organiser profileOrganiser) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.description = description;
        this.profileOrganiser = profileOrganiser;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organiser getProfileOrganiser() {
        return profileOrganiser;
    }

    public void setProfileOrganiser(Organiser profileOrganiser) {
        this.profileOrganiser = profileOrganiser;
    }
}

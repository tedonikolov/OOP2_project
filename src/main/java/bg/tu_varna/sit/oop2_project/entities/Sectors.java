package bg.tu_varna.sit.oop2_project.entities;

public class Sectors {
    private int idSectors;
    private Event event;
    private Seats seats;

    public Sectors(int idSectors, Event event, Seats seats) {
        this.idSectors = idSectors;
        this.event = event;
        this.seats = seats;
    }

    public int getIdSectors() {
        return idSectors;
    }

    public void setIdSectors(int idSectors) {
        this.idSectors = idSectors;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Seats getSeats() {
        return seats;
    }

    public void setSeats(Seats seats) {
        this.seats = seats;
    }
}

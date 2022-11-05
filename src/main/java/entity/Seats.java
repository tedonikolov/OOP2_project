package entity;

public class Seats {
    private int idSeats;
    private int amount;
    private double price;
    private int ticketPerClient;
    private Sectors sector;
    private Event event;

    public Seats(int idSeats, int amount, double price, int ticketPerClient, Sectors sector, Event event) {
        this.idSeats = idSeats;
        this.amount = amount;
        this.price = price;
        this.ticketPerClient = ticketPerClient;
        this.sector = sector;
        this.event = event;
    }

    public int getIdSeats() {
        return idSeats;
    }

    public void setIdSeats(int idSeats) {
        this.idSeats = idSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTicketPerClient() {
        return ticketPerClient;
    }

    public void setTicketPerClient(int ticketPerClient) {
        this.ticketPerClient = ticketPerClient;
    }

    public Sectors getSector() {
        return sector;
    }

    public void setSector(Sectors sector) {
        this.sector = sector;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

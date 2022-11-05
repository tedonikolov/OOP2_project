package entity;

public class Seats {
    int amount;
    int ticketPerClient;
    Sectors sector;
    Event event;

    public Seats(int amount, int ticketPerClient, Sectors sector, Event event) {
        this.amount = amount;
        this.ticketPerClient = ticketPerClient;
        this.sector = sector;
        this.event = event;
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

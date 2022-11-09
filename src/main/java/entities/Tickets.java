package entities;

public class Tickets {
    private int idTicket;
    private Event event;
    private int ticketsSold;
    private Distributor distributor;
    private int rate;

    public Tickets(int idTicket, Event event, int ticketsSold, Distributor distributor, int rate) {
        this.idTicket = idTicket;
        this.event = event;
        this.ticketsSold = ticketsSold;
        this.distributor = distributor;
        this.rate = rate;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}

package entity;

public class Tickets {
    private int idTicket;
    private Seats seat;
    private int ticketsSold;
    private Distributor distributor;

    public Tickets(int idTicket, Seats seat, int ticketsSold, Distributor distributor) {
        this.idTicket = idTicket;
        this.seat = seat;
        this.ticketsSold = ticketsSold;
        this.distributor = distributor;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public Seats getSeat() {
        return seat;
    }

    public void setSeat(Seats seat) {
        this.seat = seat;
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
}

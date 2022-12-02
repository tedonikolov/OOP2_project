package bg.tu_varna.sit.oop2_project.entities;

public class Tickets {
    private int idTicket;
    private Sectors sectors;
    private int ticketsSold;
    private Distributor distributor;
    private int rate;

    public Tickets(int idTicket, Sectors sectors, int ticketsSold, Distributor distributor, int rate) {
        this.idTicket = idTicket;
        this.sectors = sectors;
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

    public Sectors getSectors() {
        return sectors;
    }

    public void setSectors(Sectors sectors) {
        this.sectors = sectors;
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

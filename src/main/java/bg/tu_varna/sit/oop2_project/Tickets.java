package bg.tu_varna.sit.oop2_project;

public class Tickets {
    Seats seat;
    int ticketsSold;
    Distributor distributorProfile;

    public Tickets(Seats seat, int ticketsSold, Distributor distributorProfile) {
        this.seat = seat;
        this.ticketsSold = ticketsSold;
        this.distributorProfile = distributorProfile;
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

    public Distributor getDistributorProfile() {
        return distributorProfile;
    }

    public void setDistributorProfile(Distributor distributorProfile) {
        this.distributorProfile = distributorProfile;
    }
}

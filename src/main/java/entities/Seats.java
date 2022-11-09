package entities;

public class Seats {
    private int idSeats;
    private String type;
    private int amount;
    private double price;
    private int ticketPerClient;

    public Seats(int idSeats, String type, int amount, double price, int ticketPerClient) {
        this.idSeats = idSeats;
        this.type=type;
        this.amount = amount;
        this.price = price;
        this.ticketPerClient = ticketPerClient;
    }

    public int getIdSeats() {
        return idSeats;
    }

    public void setIdSeats(int idSeats) {
        this.idSeats = idSeats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}

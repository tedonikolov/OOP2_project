package bg.tu_varna.sit.oop2_project.dataLayer.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventDTO {
    private String name;
    private String address;
    private LocalDate date;
    private LocalTime time;
    private String seat;
    private int amount;
    private int sold;
    private double price;

    public EventDTO(String name, String address, LocalDate date, LocalTime time, String seat, int amount, int sold, double price) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.time = time;
        this.seat = seat;
        this.amount = amount;
        this.sold = sold;
        this.price = price;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

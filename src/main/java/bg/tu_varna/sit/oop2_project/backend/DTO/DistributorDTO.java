package bg.tu_varna.sit.oop2_project.backend.DTO;

public class DistributorDTO {
    private String name;
    private String email;
    private String phone;
    private double rating;
    private double salary;
    private String event;
    private String seat;
    private int sold;

    public DistributorDTO(String name, String email, String phone, double rating, double salary, String event, String seat, int sold) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.rating = rating;
        this.salary = salary;
        this.event = event;
        this.seat = seat;
        this.sold = sold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}

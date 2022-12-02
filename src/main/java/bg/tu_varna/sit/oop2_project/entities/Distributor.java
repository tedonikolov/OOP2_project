package bg.tu_varna.sit.oop2_project.entities;

public class Distributor {
    private Profiles profiles;
    private int idProfile;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private double rating;
    private double salary;

    public Distributor(Profiles profiles, String firstName, String lastName, String email, String phoneNumber, double rating, double salary) {
        this.profiles = profiles;
        idProfile= profiles.getIdProfile();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.salary = salary;
    }

    public Profiles getProfiles() {
        return profiles;
    }

    public void setProfiles(Profiles profiles) {
        this.profiles = profiles;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}

package bg.tu_varna.sit.oop2_project.dataLayer.entities;

public class Profiles {
    private int idProfile;
    private String username;
    private String password;
    private Roles roles;

    public Profiles(int idProfile, String username, String password, Roles roles) {
        this.idProfile = idProfile;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles_id(Roles roles) {
        this.roles = roles;
    }
}

package bg.tu_varna.sit.oop2_project.dataLayer.DTO;

public class ProfilesDTO {
    private int idProfile;
    private String username;
    private String role;

    public ProfilesDTO(int idProfile, String username, String role) {
        this.idProfile = idProfile;
        this.username = username;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

package entity;

import java.util.Objects;

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

    public String getRole(){
        return roles.getRole();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profiles profiles = (Profiles) o;
        return idProfile == profiles.idProfile && roles == profiles.roles && Objects.equals(username, profiles.username) && Objects.equals(password, profiles.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProfile, username, password, roles);
    }

    @Override
    public String toString() {
        return "Profiles{" +
                "id_profile=" + idProfile +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles_id=" + roles +
                '}';
    }
}

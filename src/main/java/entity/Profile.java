package entity;

import java.util.Objects;

public class Profile {
    private int id_profile;
    private String username;
    private String password;
    private int roles_id;

    public Profile(String username, String password, int roles_id) {
        this.username = username;
        this.password = password;
        this.roles_id = roles_id;
    }

    public int getId_profile() {
        return id_profile;
    }

    public void setId_profile(int id_profile) {
        this.id_profile = id_profile;
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

    public int getRoles_id() {
        return roles_id;
    }

    public void setRoles_id(int roles_id) {
        this.roles_id = roles_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id_profile == profile.id_profile && roles_id == profile.roles_id && Objects.equals(username, profile.username) && Objects.equals(password, profile.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_profile, username, password, roles_id);
    }

    @Override
    public String toString() {
        return "Profiles{" +
                "id_profile=" + id_profile +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles_id=" + roles_id +
                '}';
    }
}

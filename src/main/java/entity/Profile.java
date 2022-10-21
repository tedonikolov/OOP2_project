package entity;

import java.util.Objects;

public class Profile {
    private int id_profile;
    private String username;
    private String password;
    private Role role;

    public Profile(int id_profile,String username, String password, Role role) {
        this.id_profile=id_profile;
        this.username = username;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRoles_id(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id_profile == profile.id_profile && role == profile.role && Objects.equals(username, profile.username) && Objects.equals(password, profile.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_profile, username, password, role);
    }

    @Override
    public String toString() {
        return "Profiles{" +
                "id_profile=" + id_profile +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles_id=" + role +
                '}';
    }
}

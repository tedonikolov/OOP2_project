package bg.tu_varna.sit.oop2_project.dataLayer.entities;

import java.util.Objects;

public class Roles {
    private int idRole;
    private String role;

    public Roles(int idRole, String role) {
        this.idRole = idRole;
        this.role = role;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

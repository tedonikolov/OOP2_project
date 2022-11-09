package entities;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles1 = (Roles) o;
        return idRole == roles1.idRole && Objects.equals(role, roles1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id_role=" + idRole +
                ", role='" + role + '\'' +
                '}';
    }
}

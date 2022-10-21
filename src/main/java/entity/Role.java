package entity;

import java.util.Objects;

public class Role {
    private int id_role;
    private String role;

    public Role(int id_role, String role) {
        this.id_role = id_role;
        this.role = role;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
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
        Role role1 = (Role) o;
        return id_role == role1.id_role && Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_role, role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id_role=" + id_role +
                ", role='" + role + '\'' +
                '}';
    }
}

package org.example.server.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.example.server.utill.RolesEnum;

@Builder
@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "name_role",unique = true)
    private RolesEnum role;

    public Roles() {
    }

    public Roles(Long id, RolesEnum role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RolesEnum getRole() {
        return role;
    }

    public void setRole(RolesEnum role) {
        this.role = role;
    }
}

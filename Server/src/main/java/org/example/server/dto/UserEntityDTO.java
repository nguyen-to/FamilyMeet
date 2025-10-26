package org.example.server.dto;

import org.example.server.entity.UserEntity;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class UserEntityDTO {

    private Long id;
    private String email;
    private String fullName;
    private String picture;
    private Set<String> roles;

    public UserEntityDTO() {
    }
    public UserEntityDTO(UserEntity user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.picture = user.getPicture();
        this.roles = user.getRoles() != null
        ? user.getRoles().stream()
              .map(r -> r.getRole().name())
              .collect(Collectors.toSet())
        : Collections.emptySet();

    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}

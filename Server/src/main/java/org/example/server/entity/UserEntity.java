package org.example.server.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Entity
@Table(name = "user_account",
indexes = {
        @Index(name = "idx_fullName",columnList = "fullname")
}
)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "phone")
    private Long phone;
    @Column(name = "picture")
    private String picture;
    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(name = "create_at",updatable = false)
    private Timestamp createAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;

    public UserEntity() {
    }

    public UserEntity(Long id, String email, String password, String fullName, Long phone, String picture, LocalDate dateOfBirth, Timestamp createAt, Set<Roles> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.picture = picture;
        this.dateOfBirth = dateOfBirth;
        this.createAt = createAt;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}

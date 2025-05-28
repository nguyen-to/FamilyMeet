package org.example.server.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.example.server.utill.FamilyRoles;
import java.time.LocalDate;

@Builder
@Entity
@Table(name = "family_member")
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "join_at")
    private LocalDate joinAt;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private FamilyRoles roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private Family famil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public FamilyMember() {

    }

    public FamilyMember(Long id, String nickname, LocalDate joinAt, FamilyRoles roles, Family famil, UserEntity user) {
        this.id = id;
        this.nickname = nickname;
        this.joinAt = joinAt;
        this.roles = roles;
        this.famil = famil;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(LocalDate joinAt) {
        this.joinAt = joinAt;
    }

    public FamilyRoles getRoles() {
        return roles;
    }

    public void setRoles(FamilyRoles roles) {
        this.roles = roles;
    }

    public Family getFamil() {
        return famil;
    }

    public void setFamil(Family famil) {
        this.famil = famil;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

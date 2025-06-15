package org.example.server.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.example.server.utill.GroupRoles;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "group_member")
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private GroupRoles role;

    @Column(name =  "join_at")
    private LocalDate joinAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public GroupMember() {

    }

    public GroupMember(Long id, GroupRoles role, LocalDate joinAt, GroupEntity group, UserEntity user) {
        this.id = id;
        this.role = role;
        this.joinAt = joinAt;
        this.group = group;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupRoles getRole() {
        return role;
    }

    public void setRole(GroupRoles role) {
        this.role = role;
    }

    public LocalDate getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(LocalDate joinAt) {
        this.joinAt = joinAt;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

package org.example.server.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class FamilyMemberDTO {
    private Long id;
    private String nickname;
    private LocalDate datetime;
    private String avatarUrl;
    private String role;

    public FamilyMemberDTO() {
    }

    public FamilyMemberDTO(Long id, String nickname, LocalDate datetime, String avatarUrl, String role) {
        this.id = id;
        this.nickname = nickname;
        this.datetime = datetime;
        this.avatarUrl = avatarUrl;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public LocalDate getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDate datetime) {
        this.datetime = datetime;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

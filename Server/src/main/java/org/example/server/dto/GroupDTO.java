package org.example.server.dto;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public class GroupDTO {
    private Long id;
    private String name;
    private String description;
    private String avatarUrl;
    private LocalDateTime createdAt;

    public GroupDTO() {
    }

    public GroupDTO(Long id, String name, String description, String avatarUrl, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

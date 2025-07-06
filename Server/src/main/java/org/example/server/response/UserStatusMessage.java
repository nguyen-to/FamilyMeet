package org.example.server.response;

import java.time.LocalDateTime;

public class UserStatusMessage {
    private Long userId;
    private String status;
    private LocalDateTime timestamp;

    public UserStatusMessage() {
    }

    public UserStatusMessage(Long userId, String status, LocalDateTime timestamp) {
        this.userId = userId;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

package org.example.server.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.example.server.utill.MessageType;

@Entity
@Table(name = "message_archive")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "content_id")
    private UUID id;

    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType;
    
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "group_id")
    private String group;

    @Column(name = "sender_id")
    private String sender;

    public MessageEntity() {
    }

    public MessageEntity(UUID id, String content, MessageType messageType, LocalDateTime sentAt, String group, String sender) {
        this.id = id;
        this.content = content;
        this.messageType = messageType;
        this.sentAt = sentAt;
        this.group = group;
        this.sender = sender;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}

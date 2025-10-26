package org.example.server.request.chatRequest;


public class ChatMessage {
    private String roomId;
    private String sender;
    private String content;
    private String messageType;

    public ChatMessage() {
    }

    public ChatMessage(String roomId, String sender, String content, String messageType) {
        this.roomId = roomId;
        this.sender = sender;
        this.content = content;
        this.messageType = messageType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}

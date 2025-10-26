package org.example.server.service.chatservice;

import org.example.server.request.chatRequest.ChatMessage;

public interface MessageService {
    
    public void saveMessage(ChatMessage chatMessage);
    public  void deleteMessage(String messageId);
}

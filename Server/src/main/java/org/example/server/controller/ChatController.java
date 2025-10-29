package org.example.server.controller;

import lombok.RequiredArgsConstructor;
import org.example.server.request.ChatJoinedGroup;
import org.example.server.request.chatRequest.ChatMessage;
import org.example.server.service.chatservice.RedisStreamPublisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisStreamPublisher redisPubSubListener;
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        // đẩy message lên Redis Stream
        redisPubSubListener.publish(chatMessage);
        messagingTemplate.convertAndSend("/topic/group/" + chatMessage.getRoomId(), chatMessage);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatJoinedGroup chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Lưu userId vào session
        headerAccessor.getSessionAttributes().put("userEmail", chatMessage.getUserEmail());

        // Thông báo user join
        messagingTemplate.convertAndSend("/topic/group/" + chatMessage.getGroupId(),
                new ChatJoinedGroup(chatMessage.getGroupId(), chatMessage.getUserEmail(), "joined the chat"));
    }

}

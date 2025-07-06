package org.example.server.controller;

import lombok.RequiredArgsConstructor;
import org.example.server.dto.MessageDTO;
import org.example.server.entity.GroupEntity;
import org.example.server.entity.MessageEntity;
import org.example.server.entity.UserEntity;
import org.example.server.request.ChatJoinedGroup;
import org.example.server.request.ChatMessageRequest;
import org.example.server.service.GroupService;
import org.example.server.service.MessageService;
import org.example.server.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final GroupService groupService;
    private final UserService userService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageRequest chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Lấy email từ session
        String email = (String) headerAccessor.getSessionAttributes().get("userEmail");
        if (email != null) {
            GroupEntity group = groupService.getGroup(chatMessage.getGroupId());
            UserEntity userEntity = userService.findByEmail(email);

            MessageEntity message = new MessageEntity();
            message.setMessageType(chatMessage.getMessageType());
            message.setContent(chatMessage.getContent());
            message.setSender(userEntity);
            message.setSentAt(LocalDateTime.now());
            message.setGroup(group);
            messageService.saveMessage(message);

            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setContent(chatMessage.getContent());
            messageDTO.setSender(email);
            messageDTO.setSenderName(userEntity.getFullName());
            messageDTO.setType(chatMessage.getMessageType());
            messageDTO.setSenderPicture(userEntity.getPicture());
            messageDTO.setTimestamp(LocalDateTime.now());
            messagingTemplate.convertAndSend("/topic/group/" + chatMessage.getGroupId(), messageDTO);
        }
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

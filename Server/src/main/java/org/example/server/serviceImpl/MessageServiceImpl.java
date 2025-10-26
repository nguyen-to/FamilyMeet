package org.example.server.serviceImpl;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.example.server.entity.MessageEntity;
import org.example.server.request.chatRequest.ChatMessage;
import org.example.server.service.chatservice.MessageService;
import org.example.server.utill.MessageType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final org.example.server.repository.MessageRepository messageRepository;
    
    @Transactional
    @Override
    public void deleteMessage(String messageId) {
        messageRepository.deleteById(UUID.fromString(messageId)) ;
    }

    @Transactional
    @Override
    public void saveMessage(ChatMessage chatMessage) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setContent(chatMessage.getContent());
        messageEntity.setSender(chatMessage.getSender());
        messageEntity.setSentAt(LocalDateTime.now());
        messageEntity.setMessageType(MessageType.valueOf(chatMessage.getMessageType()));
        messageRepository.save(messageEntity);
    }

}

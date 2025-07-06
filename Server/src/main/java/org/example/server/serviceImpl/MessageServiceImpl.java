package org.example.server.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.server.entity.MessageEntity;
import org.example.server.service.MessageRepository;
import org.example.server.service.MessageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    @Override
    public MessageEntity saveMessage(MessageEntity message) {
        return messageRepository.save(message);
    }

}

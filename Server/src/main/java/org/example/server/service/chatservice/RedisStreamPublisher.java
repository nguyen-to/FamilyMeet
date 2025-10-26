package org.example.server.service.chatservice;

import java.util.Map;

import org.example.server.request.chatRequest.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisStreamPublisher {
    
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Value("${redis.stream.key:chat-stream}")
    private String streamKey;
    
    public void publish(ChatMessage message) {
        try {
            Map<String, String> messageMap = Map.of(
                "roomId", message.getRoomId(),
                "sender", message.getSender(),
                "content", message.getContent(),
                "messageType", message.getMessageType()
            );
            
            RecordId recordId = redisTemplate.opsForStream().add(streamKey, messageMap);
            log.info("Published message to stream with ID: {}", recordId);

        } catch (Exception e) {
            log.error("Failed to publish message: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to publish message", e);
        }
    }
}
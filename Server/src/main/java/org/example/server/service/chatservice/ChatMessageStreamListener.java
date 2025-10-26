package org.example.server.service.chatservice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.example.server.request.chatRequest.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.PendingMessages;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageStreamListener implements StreamListener<String, MapRecord<String, Object, Object>> {
    
    
    private final RedisTemplate<String, Object> redisTemplate;
    private final MessageService messageService;

    @Value("${redis.stream.key:chat-stream}")
    private String streamKey;
    
    @Value("${redis.stream.group:chat-group}")
    private String groupName;
    
    @Value("${redis.stream.dlq:chat-stream-dlq}")
    private String dlqStream;
    
    private final AtomicInteger retryCount = new AtomicInteger(0);
    private static final int MAX_RETRIES = 3;
    
   
    @Override
    public void onMessage(MapRecord<String, Object, Object> record) {
        RecordId recordId = record.getId();
        
        try {
            // Xử lý message
            processMessage(record);
            
            // Manual ACK sau khi xử lý thành công
            redisTemplate.opsForStream().acknowledge(streamKey, groupName, recordId);
            log.info("Processed and ACKed: {}", recordId);
            
            // Reset retry count nếu thành công
            retryCount.set(0);
            
        } catch (Exception e) {
            log.error("Failed to process message {}: {}", recordId, e.getMessage());
            
            // Retry logic với exponential backoff
            if (shouldRetry(record)) {
                retryMessage(record);
            } else {
                // Đã retry đủ số lần, chuyển sang DLQ
                handleFailedMessage(record);
            }
        }
    }
    
    private boolean shouldRetry(MapRecord<String, Object, Object> record) {
        // Kiểm tra số lần retry từ pending message
        try {
            PendingMessages pending = redisTemplate.opsForStream()
                .pending(streamKey, Consumer.from(groupName, "*"), 
                        Range.closed(record.getId().getValue(), record.getId().getValue()), 1L);
            
            if (pending != null && !pending.isEmpty()) {
                long deliveryCount = pending.get(0).getTotalDeliveryCount();
                return deliveryCount < MAX_RETRIES;
            }
        } catch (Exception e) {
            log.warn("Cannot check retry count: {}", e.getMessage());
        }
        return false;
    }
    
    private void retryMessage(MapRecord<String, Object, Object> record) {
        // Không ACK - để message ở pending state
        // Recovery task sẽ claim và retry sau
        log.warn("Message {} will be retried later", record.getId());
    }
    
    private void processMessage(MapRecord<String, Object, Object> record) {
        Map<Object, Object> data = record.getValue();
        // lưu message vào DB
        ChatMessage message = new ChatMessage();
        message.setRoomId(data.get("roomId").toString());
        message.setSender(data.get("sender").toString());
        message.setContent(data.get("content").toString());
        message.setMessageType(data.get("messageType").toString());

        messageService.saveMessage(message);
    }
    
    private void handleFailedMessage(MapRecord<String, Object, Object> record) {
        try {
            Map<String, String> dlqData = new HashMap<>();
            record.getValue().forEach((k, v) -> dlqData.put(String.valueOf(k), String.valueOf(v)));
            
            dlqData.put("failedAt", String.valueOf(Instant.now().toEpochMilli()));
            dlqData.put("originId", record.getId().getValue());
            dlqData.put("originStream", streamKey);
            dlqData.put("reason", "Max retries exceeded");
            
            redisTemplate.opsForStream().add(dlqStream, dlqData);
            
            // ACK để loại bỏ khỏi pending
            redisTemplate.opsForStream().acknowledge(streamKey, groupName, record.getId());
            
            log.error("Moved to DLQ: {}", record.getId());
            
        } catch (Exception e) {
            log.error("Failed to handle failed message: {}", e.getMessage(), e);
        }
    }
}

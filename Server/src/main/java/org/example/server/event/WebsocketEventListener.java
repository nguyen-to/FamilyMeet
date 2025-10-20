package org.example.server.event;

import lombok.RequiredArgsConstructor;
import org.example.server.response.UserStatusMessage;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class WebsocketEventListener {
    private final RedisTemplate<String,String> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final String ONLINE_USERS_KEY = "online_users:";
    private final String USER_SESSION_KEY = "user_session:";
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = getUserIdFromHeaders(headerAccessor);
        String sessionId = headerAccessor.getSessionId();

        if (userId != null) {
            // Lưu session vào Redis
            assert sessionId != null;
            redisTemplate.opsForHash().put(USER_SESSION_KEY + userId, sessionId, sessionId);

            // Thêm user vào danh sách online
            redisTemplate.opsForSet().add(ONLINE_USERS_KEY, userId);

            // Thông báo user online
            broadcastUserStatus(userId, "ONLINE");

            System.out.println("User " + userId + " connected with session: " + sessionId);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = getUserIdFromHeaders(headerAccessor);
        String sessionId = headerAccessor.getSessionId();

        if (userId != null) {
            // Xóa session khỏi Redis
            redisTemplate.opsForHash().delete(USER_SESSION_KEY + userId, sessionId);

            // Kiểm tra nếu user không còn session nào khác
            if (redisTemplate.opsForHash().size(USER_SESSION_KEY + userId) == 0) {
                redisTemplate.opsForSet().remove(ONLINE_USERS_KEY, userId);
                broadcastUserStatus(userId, "OFFLINE");
            }

            System.out.println("User " + userId + " disconnected");
        }
    }

    private String getUserIdFromHeaders(StompHeaderAccessor headerAccessor) {
        // Lấy userId từ session attributes
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes != null && sessionAttributes.containsKey("userId")) {
            return sessionAttributes.get("userId").toString();
        }

        // Hoặc từ native headers
        List<String> userIdHeaders = headerAccessor.getNativeHeader("userId");
        if (userIdHeaders != null && !userIdHeaders.isEmpty()) {
            return userIdHeaders.get(0);
        }

        return null;
    }
    private void broadcastUserStatus(String userId, String status) {
        UserStatusMessage statusMessage = new UserStatusMessage();
        statusMessage.setUserId(Long.parseLong(userId));
        statusMessage.setStatus(status);
        statusMessage.setTimestamp(LocalDateTime.now());

        // Gửi thông báo đến tất cả clients
        messagingTemplate.convertAndSend("/topic/user-status", statusMessage);
    }
}

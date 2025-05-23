package org.example.server.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final String RefreshTokenKey = "refresh:";
    @Value("${setTime.refreshToken}")
    private long setTimeRefreshToken;

    public void saveRefreshTokenRedis(String refreshToken,String email, String deviceId) {
        redisTemplate.opsForValue().set(RefreshTokenKey + email + deviceId, refreshToken, Duration.ofSeconds(setTimeRefreshToken));
        redisTemplate.opsForSet().add(RefreshTokenKey + email + ":devices", deviceId);
    }
    public boolean checkRefreshToken(String refreshToken,String email, String deviceId) {
        String token = redisTemplate.opsForValue().get(RefreshTokenKey + email + deviceId);
        return token != null && token.equals(refreshToken);
    }
    public Set<String> getDevice(String email) {
        return redisTemplate.opsForSet().members(RefreshTokenKey + email + ":devices");
    }
    public void removeRefreshToken(String email, String deviceId) {
        redisTemplate.delete(RefreshTokenKey + email + deviceId);
        redisTemplate.opsForSet().remove(RefreshTokenKey + email + ":devices", deviceId);
    }
}

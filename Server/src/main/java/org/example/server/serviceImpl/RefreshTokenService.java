package org.example.server.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final String RefreshTokenKey = "refresh:";
    private final String AccessTokenKey = "access_token:";
    @Value("${setTime.refreshToken}")
    private long setTimeRefreshToken;
    @Value("${setTime.accessToken}")
    private long setTimeAccessToken;
    public void saveRefreshAccessTokenRedis(String accessToken,String refreshToken,String email, String deviceId) {
        redisTemplate.opsForValue().set(setKey(RefreshTokenKey,email,deviceId), refreshToken, Duration.ofSeconds(setTimeRefreshToken));
        redisTemplate.opsForValue().set(AccessTokenKey + email, accessToken,Duration.ofSeconds(setTimeAccessToken));
    }
    public boolean checkRefreshToken(String refreshToken,String email, String deviceId) {
        String token = redisTemplate.opsForValue().get(setKey(RefreshTokenKey,email,deviceId));
        return token != null && token.equals(refreshToken);
    }
    public boolean checkAccessToken(String accessToken,String email) {
        String token = redisTemplate.opsForValue().get(AccessTokenKey + email);
        return token != null && token.equals(accessToken);
    }
    public void removeRefreshToken(String email, String deviceId) {
        redisTemplate.delete(RefreshTokenKey + email + deviceId);
        redisTemplate.opsForSet().remove(RefreshTokenKey + email + ":devices", deviceId);
    }
    private String setKey(String token,String email, String deviceId) {
        return token + email + ":" + deviceId;
    }
    public void removeAccessToken(String email, String deviceId) {
        redisTemplate.delete(AccessTokenKey + email);
    }
}

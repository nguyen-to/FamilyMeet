package org.example.server.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> void saveRedis(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public <T> void saveRedis(String key, T value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    @Override
    public void saveRedisString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void saveRedisString(String key, String value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    @Override
    public Optional<String> getRedisString(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(value != null ? value.toString() : null);
    }

    @Override
    public <T> Optional<T> getRedis(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(clazz.cast(value));
    }

    @Override
    public <T> Optional<T> getRedis(String key, TypeReference<T> typeReference) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) return Optional.empty();
        try {
            String json = objectMapper.writeValueAsString(value);
            return Optional.of(objectMapper.readValue(json, typeReference));
        } catch (Exception e) {
            throw new RuntimeException("Error converting Redis value", e);
        }
    }

    @Override
    public boolean existsKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public boolean deleteKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }
}

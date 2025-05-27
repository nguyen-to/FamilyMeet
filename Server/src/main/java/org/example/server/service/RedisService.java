package org.example.server.service;

import com.fasterxml.jackson.core.type.TypeReference;

import java.time.Duration;
import java.util.Optional;

public interface RedisService {
    public <T> void saveRedis(String key, T value);
    public <T> void saveRedis(String key, T value, Duration timeout);
    public <T> Optional<T> getRedis(String key,Class<T> clazz);
    public <T> Optional<T> getRedis(String key, TypeReference<T> typeReference);
    public void saveRedisString(String key, String value);
    public void saveRedisString(String key, String value, Duration timeout);
    public Optional<String> getRedisString(String key);
    public boolean existsKey(String key);
    public boolean deleteKey(String key);
}

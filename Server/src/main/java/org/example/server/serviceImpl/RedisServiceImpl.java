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
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisServiceImpl(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    // Save redis with object class
    @Override
    public <T> void saveRedis(String key, T value) {
        try{
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            throw new RuntimeException("Error saving redis", e);
        }
    }

    // Save redis with Array object class
    @Override
    public <T> void saveRedis(String key, T value, Duration timeout) {
        try{
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key,json,timeout);
        }catch (Exception e) {
            throw new RuntimeException("Error saving redis", e);
        }
    }

    // save redis value String
    @Override
    public void saveRedisString(String key, String value) {
        try{
            redisTemplate.opsForValue().set(key, value);
        }catch (Exception e) {
            throw new RuntimeException("Error saving redis", e);
        }
    }

    // save redis value String with key life
    @Override
    public void saveRedisString(String key, String value, Duration timeout) {
        try{
            redisTemplate.opsForValue().set(key,value,timeout);
        }catch (Exception e) {
            throw new RuntimeException("Error saving redis", e);
        }
    }

    @Override
    public Optional<String> getRedisString(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    //Get Object class Redis
    @Override
    public <T> Optional<T> getRedis(String key, Class<T> clazz) {
        try{
            String json = redisTemplate.opsForValue().get(key);
            if(json == null || json.isEmpty()) {
                return Optional.empty();
            }
            T value = objectMapper.readValue(json, clazz);
            return Optional.of(value);
        } catch (Exception e) {
            throw new RuntimeException("Error getting redis", e);
        }
    }

    //Get Array Object class Redis
    @Override
    public <T> Optional<T> getRedis(String key, TypeReference<T> typeReference) {
        try{
            String json = redisTemplate.opsForValue().get(key);
            if(json == null || json.isEmpty()) {
                return Optional.empty();
            }
            T value = objectMapper.readValue(json, typeReference);
            return Optional.of(value);
        }catch (Exception e) {
            throw new RuntimeException("Error getting redis", e);
        }
    }


    // Is key redis
    @Override
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }
    // delete Key redis
    @Override
    public boolean deleteKey(String key) {
        try{
            return redisTemplate.delete(key);
        }catch (Exception e) {
            return false;
        }
    }
}

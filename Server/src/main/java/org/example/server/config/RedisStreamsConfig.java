package org.example.server.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.example.server.service.chatservice.ChatMessageStreamListener;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class RedisStreamsConfig {
    
    
    @Value("${redis.stream.key:chat-stream}")
    private String streamKey;
    
    @Value("${redis.stream.group:chat-group}")
    private String groupName;
    
    @Value("${redis.stream.consumer:chat-consumer}")
    private String consumerName;

    private final RedisConnectionFactory redisConnectionFactory;

    private final ChatMessageStreamListener streamListener;

    private final RedisTemplate<String, Object> redisTemplate;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public StreamMessageListenerContainer<String, MapRecord<String, Object, Object>>
    streamMessageListenerContainer() {

        createConsumerGroupIfNotExists();

        StreamMessageListenerContainerOptions<String, MapRecord<String, Object, Object>> options =
                StreamMessageListenerContainerOptions.builder()
                        .pollTimeout(Duration.ofSeconds(2))
                        .batchSize(10)
                        .targetType(MapRecord.class) 
                        .hashKeySerializer(new StringRedisSerializer()) 
                        .hashValueSerializer(new GenericJackson2JsonRedisSerializer())
                        .build();

        StreamMessageListenerContainer<String, MapRecord<String, Object, Object>> container =
                StreamMessageListenerContainer.create(redisConnectionFactory, options);

        container.receive(
                Consumer.from(groupName, consumerName),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
                streamListener
        );

        return container;
    }

    
    private void createConsumerGroupIfNotExists() {
        try {
            redisTemplate.opsForStream().createGroup(streamKey, groupName);
        } catch (Exception e) {
            // Group đã tồn tại
        }
    }
}

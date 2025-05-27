package org.example.server.serviceImpl;

import org.example.server.entity.UserEntity;
import org.example.server.repository.UserRepository;
import org.example.server.service.RedisService;
import org.example.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
public class UserEntityImpl implements UserService {
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final StringBuffer UserKey = new StringBuffer("user:");

    @Autowired
    public UserEntityImpl(UserRepository userRepository, RedisService redisService) {
        this.userRepository = userRepository;
        this.redisService = redisService;
    }

    @Transactional
    @Override
    public UserEntity findByEmail(String email) {
        UserKey.append(email);
        Optional<UserEntity> userEntity = redisService.getRedis(UserKey.toString(), UserEntity.class);
        if (userEntity.isPresent()) {
            return userEntity.get();
        }else{
            Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
            optionalUserEntity.ifPresent(entity -> redisService.saveRedis(UserKey.toString(), entity));
            return optionalUserEntity.orElse(null);
        }
    }

    @Transactional
    @Override
    public UserEntity saveUserEntity(UserEntity userEntity) {
        UserEntity savedUserEntity = userRepository.save(userEntity);
        UserKey.append(userEntity.getEmail());
        redisService.saveRedis(UserKey.toString(), savedUserEntity, Duration.ofHours(10));
        return savedUserEntity;
    }

    @Transactional
    @Override
    public void deleteUserEntity(String email) {
        UserKey.append(email);
        redisService.deleteKey(UserKey.toString());
        userRepository.deleteByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public void changePassword(String email, String newPassword) {
        UserKey.append(email);
        redisService.deleteKey(UserKey.toString());
        userRepository.updatePassword(email, newPassword);
    }

    @Transactional
    @Override
    public UserEntity updateUserEntity(UserEntity userEntity) {
        UserEntity savedUserEntity = userRepository.save(userEntity);
        UserKey.append(userEntity.getEmail());
        redisService.saveRedis(UserKey.toString(), savedUserEntity, Duration.ofHours(10));
        return savedUserEntity;
    }
}

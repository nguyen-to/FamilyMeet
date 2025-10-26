package org.example.server.serviceImpl;

import org.example.server.dto.UserEntityDTO;
import org.example.server.entity.Roles;
import org.example.server.entity.UserEntity;
import org.example.server.repository.UserRepository;
import org.example.server.service.RedisService;
import org.example.server.service.UserService;
import org.example.server.utill.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserEntityImpl implements UserService {
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final String UserKey = "user:";

    @Autowired
    public UserEntityImpl(UserRepository userRepository, RedisService redisService) {
        this.userRepository = userRepository;
        this.redisService = redisService;
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findByEmail(String email) {
        // Lấy từ Redis
        Optional<UserEntityDTO> dtoOpt = redisService.getRedis(UserKey + email, UserEntityDTO.class);

        if (dtoOpt.isPresent()) {
            UserEntityDTO dto = dtoOpt.get();

            // Map DTO -> Entity
            UserEntity entity = new UserEntity();
            entity.setId(dto.getId());
            entity.setEmail(dto.getEmail());
            entity.setFullName(dto.getFullName());
            entity.setPicture(dto.getPicture());

            // Map roles
            if (dto.getRoles() != null) {
                Set<Roles> roles = dto.getRoles().stream()
                        .map(r -> {
                            Roles role = new Roles();
                            role.setRole(RolesEnum.valueOf(r)); // Nếu r là enum name
                            return role;
                        })
                        .collect(Collectors.toSet());
                entity.setRoles(roles);
            } else {
                entity.setRoles(Collections.emptySet());
            }

            return entity;
        } else {
            // Fallback: lấy từ DB và lưu vào Redis
            Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
            optionalUserEntity.ifPresent(entity -> {
                UserEntityDTO dto = new UserEntityDTO(entity);
                redisService.saveRedis(UserKey + email, dto, Duration.ofHours(10));
            });
            return optionalUserEntity.orElse(null);
        }
    }


    @Transactional
    @Override
    public UserEntity saveUserEntity(UserEntity userEntity) {
        UserEntity savedUserEntity = userRepository.save(userEntity);
        UserEntityDTO dto = new UserEntityDTO(savedUserEntity);
        redisService.saveRedis(UserKey + savedUserEntity.getEmail(), dto, Duration.ofHours(10));
        return savedUserEntity;
    }

    @Transactional
    @Override
    public UserEntity updateUserEntity(UserEntity userEntity) {
        UserEntity savedUserEntity = userRepository.save(userEntity);
        UserEntityDTO dto = new UserEntityDTO(savedUserEntity);
        redisService.saveRedis(UserKey + savedUserEntity.getEmail(), dto, Duration.ofHours(10));
        return savedUserEntity;
    }

    @Transactional
    @Override
    public void deleteUserEntity(String email) {
        redisService.deleteKey(UserKey + email);
        userRepository.deleteByEmail(email);
    }
    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public void changePassword(String email, String newPassword) {
        redisService.deleteKey(UserKey + email);
        userRepository.updatePassword(email, newPassword);
    }


}

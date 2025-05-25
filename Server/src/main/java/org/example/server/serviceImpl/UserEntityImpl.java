package org.example.server.serviceImpl;

import org.example.server.entity.UserEntity;
import org.example.server.repository.UserRepository;
import org.example.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserEntityImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserEntityImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    @Override
    public UserEntity saveUserEntity(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public void deleteUserEntity(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public void changePassword(String email, String newPassword) {
        userRepository.updatePassword(email, newPassword);
    }

    @Transactional
    @Override
    public UserEntity updateUserEntity(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}

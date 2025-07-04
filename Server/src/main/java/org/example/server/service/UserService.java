package org.example.server.service;
import org.example.server.entity.UserEntity;

public interface UserService {
    public UserEntity findByEmail(String email);
    public UserEntity saveUserEntity(UserEntity userEntity);
    public UserEntity updateUserEntity(UserEntity userEntity);
    public void deleteUserEntity(String email);
    public boolean existsByEmail(String email);
    public void changePassword(String email, String newPassword);
}

package org.example.server.service;
import org.example.server.entity.UserEntity;
import java.util.Optional;

public interface UserService {
    public UserEntity findByEmail(String email);
    public UserEntity saveUserEntity(UserEntity userEntity);
    public UserEntity updateUserEntity(UserEntity userEntity);
    public void deleteUserEntity(String email);
    public boolean existsByEmail(String email);

}

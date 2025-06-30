package org.example.server.service.userservice;

import jakarta.transaction.Transactional;
import org.example.server.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private final UserEntity user;

    public CustomUserDetails(UserEntity userEntity) {
        this.user = userEntity;
    }

    public UserEntity getUser() {
        return user;
    }

    @Transactional
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().filter(Objects::nonNull).map(roles -> new SimpleGrantedAuthority(roles.getRole().name())).collect(Collectors.toList());
    }

    public String getFullName() {
        return user.getFullName();
    }
    public String getPicture() {
        return user.getPicture();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

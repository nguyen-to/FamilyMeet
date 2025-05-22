package org.example.server.dto;

import org.example.server.service.userservice.CustomUserDetails;

import java.util.Set;

public class UserEntityDTO {
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private Set<CustomUserDetails> customUserDetails;
}

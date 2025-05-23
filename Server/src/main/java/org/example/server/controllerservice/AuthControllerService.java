package org.example.server.controllerservice;

import lombok.RequiredArgsConstructor;
import org.example.server.entity.Roles;
import org.example.server.entity.UserEntity;
import org.example.server.request.LoginRequest;
import org.example.server.request.RegisterRequest;
import org.example.server.response.LoginResponse;
import org.example.server.response.RefreshTokenResponse;
import org.example.server.response.formdata.DataFormResponse;
import org.example.server.service.JwtService;
import org.example.server.service.RolesService;
import org.example.server.service.UserService;
import org.example.server.service.userservice.CustomUserDetails;
import org.example.server.serviceImpl.RefreshTokenService;
import org.example.server.utill.RolesEnum;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthControllerService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RolesService rolesService;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse ActiveLogin(LoginRequest loginRequest) {
        // xác thực user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        String email = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateAccessToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(authentication);
        // save refresh token for redis
        refreshTokenService.saveRefreshTokenRedis(refreshToken,email,loginRequest.getDeviceId());
        return LoginResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public DataFormResponse<String> ActiveRegister(RegisterRequest registerRequest) {
        if(userService.existsByEmail(registerRequest.getEmail())) {
            return DataFormResponse.<String>builder()
                    .message("Email already exists")
                    .build();
        }
        // find ang get roles for RolesEntity
        Roles roles = rolesService.getRoles(RolesEnum.USER);
        Set<Roles> rolesSet = new HashSet<>();
        rolesSet.add(roles);

        UserEntity userEntity = UserEntity.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .fullName(registerRequest.getFullName())
                .roles(rolesSet)
                .build();
        userService.saveUserEntity(userEntity);
        return DataFormResponse.<String>builder()
                .message("Register Successful")
                .build();
    }

    public DataFormResponse<RefreshTokenResponse> ActiveRefreshToken(String refreshToken,String deviceId) {

        // check refresh Token for redis
        String emailRedis = jwtService.extractEmailToToken(refreshToken);
        if(!refreshTokenService.checkRefreshToken(refreshToken,emailRedis,deviceId)) {
            throw new IllegalStateException("Invalid refresh token");
        }

        if(!jwtService.validateRefreshToken(refreshToken)) {
            throw new IllegalStateException("Invalid refresh token");
        }
        String email = jwtService.extractEmailToToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if(userDetails == null) {
            throw new IllegalStateException("User not found");
        }

        UsernamePasswordAuthenticationToken auth= new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        String accessToken = jwtService.generateAccessToken(auth);
        return DataFormResponse.<RefreshTokenResponse>builder()
                .message("Refresh token successful")
                .data(RefreshTokenResponse.builder()
                        .accessToken(accessToken)
                        .build())
                .build();
    }

    public DataFormResponse<String> ActiveLogout(String deviceId, Principal principal) {
        //  remote refresh token for redis
        String email = principal.getName();
        refreshTokenService.removeRefreshToken(email,deviceId);
        return DataFormResponse.<String>builder()
                .message("Logout Successful")
                .build();
    }
}

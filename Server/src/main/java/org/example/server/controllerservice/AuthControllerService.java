package org.example.server.controllerservice;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.server.entity.Roles;
import org.example.server.entity.UserEntity;
import org.example.server.request.authrequest.ChangeRequest;
import org.example.server.request.authrequest.LoginRequest;
import org.example.server.request.authrequest.RegisterRequest;
import org.example.server.response.authresponse.LoginResponse;
import org.example.server.response.authresponse.RefreshTokenResponse;
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

@Slf4j
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
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateAccessToken(authentication, loginRequest.getDeviceId());
        String refreshToken = jwtService.generateRefreshToken(authentication,loginRequest.getDeviceId());
        // save refresh token for redis
        refreshTokenService.saveRefreshAccessTokenRedis(token,refreshToken,email,loginRequest.getDeviceId());
        return LoginResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .email(userDetails.getUsername())
                .fullName(userDetails.getFullName())
                .picture(userDetails.getPicture())
                .deviceId(loginRequest.getDeviceId())
                .build();
    }
    public LoginResponse ActiveLoginFirebase(LoginRequest loginRequest) {
        String email = null;
        String uId = null;
        String fullName = null;
        String picture = null;
        try{
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(loginRequest.getPassword()); // authentication IdToken with Firebase Admin
            email = firebaseToken.getEmail();  // get email to idToken
            uId = firebaseToken.getUid();       // get uId to idToken
            fullName = firebaseToken.getName();
            picture = firebaseToken.getPicture();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        UserEntity userEntity = userService.findByEmail(email);

        CustomUserDetails customUserDetails = null;
        log.info("user active: {} ", userEntity);
        // user is null save database
        if(userEntity == null) {
            Roles roles = rolesService.getRoles(RolesEnum.USER);
            Set<Roles> rolesSet = new HashSet<>();
            rolesSet.add(roles);
            UserEntity userEntity1 = UserEntity.builder()
                    .email(email)
                    .password(passwordEncoder.encode(uId))
                    .roles(rolesSet)
                    .fullName(fullName)
                    .picture(picture)
                    .build();
            userService.saveUserEntity(userEntity1);
            customUserDetails = new CustomUserDetails(userEntity1);

        }else{
            customUserDetails = new CustomUserDetails(userEntity);
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                customUserDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String accessToken = jwtService.generateAccessToken(auth,loginRequest.getDeviceId());
        String refreshToken = jwtService.generateRefreshToken(auth,loginRequest.getDeviceId());
        // save refresh token for redis
        refreshTokenService.saveRefreshAccessTokenRedis(accessToken,refreshToken,email,loginRequest.getDeviceId());
        return LoginResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .email(email)
                .picture(picture)
                .fullName(fullName)
                .deviceId(loginRequest.getDeviceId())
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
        String emailRedis = jwtService.extractDeviceIdFromToken(refreshToken);
        if(!refreshTokenService.checkRefreshToken(refreshToken,emailRedis,deviceId)) {
            throw new IllegalStateException("Invalid refresh token");
        }

        if(!jwtService.validateRefreshToken(refreshToken)) {
            throw new IllegalStateException("Invalid refresh token");
        }
        String email = jwtService.extractDeviceIdFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if(userDetails == null) {
            throw new IllegalStateException("User not found");
        }

        UsernamePasswordAuthenticationToken auth= new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        String accessToken = jwtService.generateAccessToken(auth,deviceId);
        refreshTokenService.saveRefreshAccessTokenRedis(accessToken,refreshToken,email,deviceId);
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

    public DataFormResponse<String> ActiveChangePassword(ChangeRequest changeRequest) {
        UserEntity userEntity = userService.findByEmail(changeRequest.getEmail());
        log.info("userEntity:"+userEntity);
        if(userEntity == null) {
            return DataFormResponse.<String>builder()
                    .message("User not found")
                    .build();
        }
        try {
            userService.changePassword(changeRequest.getEmail(), passwordEncoder.encode(changeRequest.getNewPassword()));
            log.info("errorr");
        }catch (Exception e) {
            throw new IllegalStateException("Invalid email");
        }
        return DataFormResponse.<String>builder()
                .message("Change Password Successful")
                .build();
    }


}

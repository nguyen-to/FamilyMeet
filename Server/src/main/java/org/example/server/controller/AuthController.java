package org.example.server.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.server.controllerservice.AuthControllerService;
import org.example.server.request.authrequest.ChangeRequest;
import org.example.server.request.authrequest.LoginRequest;
import org.example.server.request.authrequest.RegisterRequest;
import org.example.server.response.authresponse.LoginResponse;
import org.example.server.response.authresponse.RefreshTokenResponse;
import org.example.server.response.formdata.DataFormResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthControllerService authControllerService;
    @PostMapping("/login")
    public ResponseEntity<DataFormResponse<LoginResponse>> AuthLogin(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = authControllerService.ActiveLogin(loginRequest);
        String refreshToken = loginResponse.getRefreshToken();
        // Set Cookie
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/api/auth/refresh-token")
                .sameSite("Lax")
                .maxAge(7 * 24 * 60 * 60).build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        loginResponse.setRefreshToken(null);
        return ResponseEntity.ok().body(DataFormResponse.<LoginResponse>builder()
                .message("Login successful")
                .data(loginResponse)
                .build());
    }

    @PostMapping("/firebase")
    public ResponseEntity<DataFormResponse<LoginResponse>> AuthLoginFirebase(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authControllerService.ActiveLoginFirebase(loginRequest);
        String refreshToken = loginResponse.getRefreshToken();
        // Set Cookie
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/api/auth/refresh-token")
                .sameSite("Lax")
                .maxAge(7 * 24 * 60 * 60).build();
        loginResponse.setRefreshToken(null);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(DataFormResponse.<LoginResponse>builder()
                        .message("Login successful")
                        .data(loginResponse)
                        .build());
    }
    @PostMapping("/register")
    public ResponseEntity<DataFormResponse<String>> AuthRegister(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authControllerService.ActiveRegister(registerRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<DataFormResponse<RefreshTokenResponse>> AuthRefreshToken(@CookieValue(name = "refreshToken")String refreshToken,@RequestParam("deviceId")String deviceId) {
        return ResponseEntity.ok().body(authControllerService.ActiveRefreshToken(refreshToken,deviceId));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<DataFormResponse<String>> changePassword(@RequestBody ChangeRequest changeRequest) {
        return ResponseEntity.ok(authControllerService.ActiveChangePassword(changeRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<DataFormResponse<String>> AuthLogout(@RequestParam("deviceId")String deviceId,Principal principal) {
        return ResponseEntity.ok(authControllerService.ActiveLogout(deviceId,principal));
    }
    @GetMapping("/get")
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok("hello");
    }
}

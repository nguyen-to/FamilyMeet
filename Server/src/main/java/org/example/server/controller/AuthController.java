package org.example.server.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.server.controllerservice.AuthControllerService;
import org.example.server.request.LoginRequest;
import org.example.server.request.RegisterRequest;
import org.example.server.response.LoginResponse;
import org.example.server.response.RefreshTokenResponse;
import org.example.server.response.RegisterResponse;
import org.example.server.response.formdata.DataFormResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PostMapping("/register")
    public ResponseEntity<DataFormResponse<String>> AuthRegister(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authControllerService.ActiveRegister(registerRequest));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<DataFormResponse<RefreshTokenResponse>> AuthRefreshToken(@CookieValue(name = "refreshToken")String refreshToken,@RequestParam("deviceId")String deviceId) {
        return ResponseEntity.ok().body(authControllerService.ActiveRefreshToken(refreshToken,deviceId));
    }

    @PostMapping("/logout")
    public ResponseEntity<DataFormResponse<String>> AuthLogout(@RequestParam("deviceId")String deviceId,Principal principal) {
        return ResponseEntity.ok(authControllerService.ActiveLogout(deviceId,principal));
    }
}

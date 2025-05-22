package org.example.server.controller;

import lombok.RequiredArgsConstructor;
import org.example.server.controllerservice.AuthControllerService;
import org.example.server.request.LoginRequest;
import org.example.server.request.RegisterRequest;
import org.example.server.response.LoginResponse;
import org.example.server.response.RefreshTokenResponse;
import org.example.server.response.RegisterResponse;
import org.example.server.response.formdata.DataFormResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthControllerService authControllerService;
    @PostMapping("/login")
    public ResponseEntity<DataFormResponse<LoginResponse>> AuthLogin(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authControllerService.ActiveLogin(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<DataFormResponse<String>> AuthRegister(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authControllerService.ActiveRegister(registerRequest));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<DataFormResponse<RefreshTokenResponse>> AuthRefreshToken(@RequestParam(name = "token")String token) {
        return ResponseEntity.ok(authControllerService.ActiveRefreshToken(token));
    }

}

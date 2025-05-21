package org.example.server.controller;

import lombok.RequiredArgsConstructor;
import org.example.server.controllerservice.AuthControllerService;
import org.example.server.request.LoginRequest;
import org.example.server.response.formdata.DataFormResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthControllerService authControllerService;
    @PostMapping
    public ResponseEntity<DataFormResponse<String>> AuthLogin(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authControllerService.ActiveLogin(loginRequest));
    }
}

package com.gc.zelda_api.controller;

import com.gc.zelda_api.dto.auth.LoginRequest;
import com.gc.zelda_api.dto.auth.LoginResponse;
import com.gc.zelda_api.dto.auth.RefreshRequest;
import com.gc.zelda_api.dto.auth.RegisterRequest;
import com.gc.zelda_api.model.User;
import com.gc.zelda_api.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@Validated
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        User user = authService.authenticate(loginRequest);
        return ResponseEntity.ok(
                authService.getResponseWithNewCredentials(user)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid RegisterRequest registerRequest
    ){
        authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody @Valid RefreshRequest refreshRequest){
        User authenticatedUser = authService
                .refreshUser(refreshRequest.getRefresh_token());

        return ResponseEntity.ok(authService.getResponseWithNewCredentials(authenticatedUser));
    }
}
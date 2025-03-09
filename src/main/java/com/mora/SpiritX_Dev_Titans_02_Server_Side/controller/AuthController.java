package com.mora.SpiritX_Dev_Titans_02_Server_Side.controller;

import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.AuthRequest;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.AuthResponse;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.SignUpRequest;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody SignUpRequest request) {
        AuthResponse response = authService.registerUser(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}

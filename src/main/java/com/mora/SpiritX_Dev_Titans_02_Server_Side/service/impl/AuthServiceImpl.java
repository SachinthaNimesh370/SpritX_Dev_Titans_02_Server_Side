package com.mora.SpiritX_Dev_Titans_02_Server_Side.service.impl;

import com.mora.SpiritX_Dev_Titans_02_Server_Side.config.JwtUtil;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.AuthRequest;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.AuthResponse;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.SignUpRequest;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.entity.User;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.repository.UserRepository;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void registerUser(SignUpRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .active(false)
                .build();

        userRepository.save(user);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Check if the user is active
        if (!user.isActive()) {
            return new AuthResponse(
                    403,                         // HTTP Status Code (Forbidden)
                    "Your account is pending admin approval.", // Message
                    null,                         // No token since login failed
                    user.getRole()                // Role (but the login is rejected)
            );
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(
                200,                        // HTTP Status Code
                "Login successful",         // Success message
                token,                      // JWT Token
                user.getRole()              // Role (ADMIN or USER)
        );
    }

}

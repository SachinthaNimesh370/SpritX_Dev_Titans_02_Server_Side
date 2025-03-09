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
    public AuthResponse registerUser(SignUpRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());

        if (existingUser.isPresent()) {
            return new AuthResponse(409, "User already exists", null, null); // 409 Conflict
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .active(false) // Account inactive by default
                .build();

        userRepository.save(user);

        return new AuthResponse(201, "User registered successfully. Awaiting admin approval.", null, user.getRole());
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null) {
            return new AuthResponse(404, "User not found", null, null); // 404 Not Found
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse(401, "Invalid credentials", null, null); // 401 Unauthorized
        }

        if (!user.isActive()) {
            return new AuthResponse(403, "Your account is pending admin approval.", null, user.getRole()); // 403 Forbidden
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(200, "Login successful", token, user.getRole()); // 200 OK
    }

}

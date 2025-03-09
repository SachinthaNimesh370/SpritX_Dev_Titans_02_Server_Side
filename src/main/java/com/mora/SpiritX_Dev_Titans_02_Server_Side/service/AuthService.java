package com.mora.SpiritX_Dev_Titans_02_Server_Side.service;

import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.AuthRequest;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.AuthResponse;
import com.mora.SpiritX_Dev_Titans_02_Server_Side.dto.SignUpRequest;

public interface AuthService {
    void registerUser(SignUpRequest request);
    AuthResponse authenticate(AuthRequest request);
}

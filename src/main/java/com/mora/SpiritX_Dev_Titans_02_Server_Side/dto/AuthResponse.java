package com.mora.SpiritX_Dev_Titans_02_Server_Side.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private int statusCode;
    private String message;
    private String token;
    private String role;  // Add user role (ADMIN/USER)
}

package com.mora.SpiritX_Dev_Titans_02_Server_Side.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String role; // "admin" or "user"
}

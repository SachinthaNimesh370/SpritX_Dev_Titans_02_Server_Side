package com.mora.SpiritX_Dev_Titans_02_Server_Side.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String role; // "admin" or "user"
    private boolean active = false; // Default to deactivated
}

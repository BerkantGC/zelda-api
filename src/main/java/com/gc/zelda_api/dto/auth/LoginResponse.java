package com.gc.zelda_api.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String username;
    private String email;
    private String access_token;
    private String refresh_token;
    private Long access_token_expires_at;
    private Long refresh_token_expires_at;
}

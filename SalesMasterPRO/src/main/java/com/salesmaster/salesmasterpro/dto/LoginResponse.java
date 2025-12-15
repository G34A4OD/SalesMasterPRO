package com.salesmaster.salesmasterpro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String username;
    private String token;
}


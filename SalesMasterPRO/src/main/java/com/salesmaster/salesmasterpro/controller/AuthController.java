package com.salesmaster.salesmasterpro.controller;

import com.salesmaster.salesmasterpro.dto.LoginRequest;
import com.salesmaster.salesmasterpro.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de autenticación")
public class AuthController {

    @PostMapping("/login")
    @Operation(summary = "Login básico", description = "Valida credenciales y retorna un token simulado.")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        boolean valid = "admin".equalsIgnoreCase(request.getUsername()) && "123".equals(request.getPassword());

        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.builder()
                            .message("Credenciales inválidas")
                            .username(request.getUsername())
                            .token(null)
                            .build());
        }

        LoginResponse response = LoginResponse.builder()
                .message("Login exitoso")
                .username("Admin")
                .token("dummy-token")
                .build();

        return ResponseEntity.ok(response);
    }
}


package com.salesmaster.salesmasterpro.service;

import com.salesmaster.salesmasterpro.dto.LoginRequest;
import com.salesmaster.salesmasterpro.dto.LoginResponse;
import com.salesmaster.salesmasterpro.dto.RegisterRequest;
import com.salesmaster.salesmasterpro.entity.User;
import com.salesmaster.salesmasterpro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = Objects.requireNonNull(userRepository.save(user));
        String token = jwtService.generateToken(savedUser);

        return LoginResponse.builder()
                .message("Registro exitoso")
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .token(token)
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            throw new IllegalStateException("Principal de seguridad inválido");
        }
        User user = (User) principal;

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .message("Login exitoso")
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .build();
    }
}



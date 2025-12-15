package com.salesmaster.salesmasterpro.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "El email es obligatorio")
    @Size(min = 3, max = 120, message = "El email debe tener entre 3 y 120 caracteres")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 3, max = 50, message = "La contraseña debe tener entre 3 y 50 caracteres")
    private String password;
}


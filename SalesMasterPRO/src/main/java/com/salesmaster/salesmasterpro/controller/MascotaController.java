package com.salesmaster.salesmasterpro.controller;

import com.salesmaster.salesmasterpro.entity.Mascota;
import com.salesmaster.salesmasterpro.repository.MascotaRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
@Tag(name = "Mascotas", description = "CRUD de mascotas")
public class MascotaController {

    private final MascotaRepository mascotaRepository;

    @GetMapping
    public List<Mascota> listar() {
        return mascotaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtener(@PathVariable @NonNull Long id) {
        return mascotaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mascota> crear(@Valid @RequestBody @NonNull Mascota mascota) {
        Mascota guardada = mascotaRepository.save(mascota);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> actualizar(@PathVariable @NonNull Long id, @Valid @RequestBody @NonNull Mascota mascota) {
        return mascotaRepository.findById(id)
                .map(actual -> {
                    actual.setAlias(mascota.getAlias());
                    actual.setEspecie(mascota.getEspecie());
                    actual.setRaza(mascota.getRaza());
                    actual.setFotoUrl(mascota.getFotoUrl());
                    Mascota actualizado = mascotaRepository.save(actual);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @NonNull Long id) {
        if (!mascotaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mascotaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}



package com.salesmaster.salesmasterpro.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Media", description = "Carga de archivos")
public class MediaController {

    private static final Path UPLOAD_DIR = Paths.get("uploads");

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Archivo vacío");
        }

        try {
            Files.createDirectories(UPLOAD_DIR);
            String originalName = StringUtils.cleanPath(
                    java.util.Objects.requireNonNull(
                            java.util.Objects.toString(file.getOriginalFilename(), "archivo")
                    )
            );
            String filename = UUID.randomUUID() + "_" + originalName;
            Path destination = UPLOAD_DIR.resolve(filename);

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            String url = "/uploads/" + filename;
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo guardar el archivo");
        }
    }
}



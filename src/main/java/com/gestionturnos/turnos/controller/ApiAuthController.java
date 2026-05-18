package com.gestionturnos.turnos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiAuthController {

    @PostMapping("/login")
    public ResponseEntity<?> apiLogin(@RequestBody LoginRequest request) {
        // Spring Security manejará la autenticación automáticamente
        // Solo necesitamos devolver una respuesta exitosa
        return ResponseEntity.ok().body("Autenticación exitosa");
    }

    // Clase interna para el request
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters y Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
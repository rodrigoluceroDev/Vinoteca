package com.tequila.ecommerce.vinoteca.controllers;

import com.tequila.ecommerce.vinoteca.dto.LoginRequest; // Import del DTO externo
import com.tequila.ecommerce.vinoteca.models.User;
import com.tequila.ecommerce.vinoteca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // 1. Validación básica
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null || 
            loginRequest.getEmail().isBlank() || loginRequest.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("Email y contraseña son obligatorios");
        }

        // 2. Normalización de inputs
        String email = loginRequest.getEmail().toLowerCase().trim();
        String password = loginRequest.getPassword().trim();

        // 3. Buscar usuario en BD
        User user = userService.encontrarPorEmail(email);
        if (user == null) {
            return ResponseEntity.status(401).body("Usuario no registrado");
        }

        // 4. Comparación segura con BCrypt
        if (passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.ok("Login exitoso para: " + user.getNombre());
        } else {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }
    }
}
    
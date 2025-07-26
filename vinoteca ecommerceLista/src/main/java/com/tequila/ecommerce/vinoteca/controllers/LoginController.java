package com.tequila.ecommerce.vinoteca.controllers;

import com.tequila.ecommerce.vinoteca.models.User;
import com.tequila.ecommerce.vinoteca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller // Cambiado de @RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, 
                       HttpSession session) { // Añadido HttpSession
        
        User user = userService.encontrarPorEmail(loginRequest.getEmail());
        
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            session.setAttribute("currentUser", user.getNombre()); // Guarda nombre en sesión
            return "redirect:/"; // Redirige a página principal
        } else {
            return "redirect:/login?error"; // Redirige con parámetro de error
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Elimina la sesión
        return "redirect:/login?logout"; // Redirige con parámetro de logout
    }

    // Clase interna se mantiene igual
    public static class LoginRequest {
        private String email;
        private String password;

        // getters y setters
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }
}

package com.tequila.vinoteca.controllers;

import com.tequila.vinoteca.models.User;
import com.tequila.vinoteca.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    private final UserService userService;

    public RegistroController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new User());
        return "registro";
    }

    @PostMapping
    public String registrarUsuario(@ModelAttribute User usuario, Model model) {
        try {
            userService.registrarUsuario(usuario.getEmail(), usuario.getPassword());
            return "redirect:/login?registroExitoso";
        } catch (Exception e) {
            model.addAttribute("error", "El email ya está registrado");
            return "registro";
        }
    }
}
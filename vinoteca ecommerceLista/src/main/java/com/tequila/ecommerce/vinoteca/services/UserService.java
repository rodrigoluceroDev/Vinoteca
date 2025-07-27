package com.tequila.vinoteca.services;

import com.tequila.vinoteca.models.User;
import com.tequila.vinoteca.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registrarUsuario(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email ya registrado");
        }
        User usuario = new User();
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        userRepository.save(usuario);
    }
}
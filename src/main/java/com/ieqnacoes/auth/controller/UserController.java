package com.ieqnacoes.auth.controller;

import com.ieqnacoes.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ieqnacoes.auth.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            User createdUser = userService.registerUser(user.getEmail(), user.getUserName(), user.getPassword(), user.getRole());
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso: " + createdUser.getEmail());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Optional<User> authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
        return authenticatedUser.map(u -> ResponseEntity.ok("Login bem-sucedido! Usuário: " + u.getUserName() + ", Perfil: " + u.getRole()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos"));
    }
}

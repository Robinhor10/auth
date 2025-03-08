package com.ieqnacoes.auth.controller;

import com.ieqnacoes.auth.dto.ApiResponse;
import com.ieqnacoes.auth.dto.UserResponseDTO;
import com.ieqnacoes.auth.model.User;
import com.ieqnacoes.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@RequestBody User user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null || user.getUserName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, "Campos obrigatórios estão faltando", null));
            }

            User createdUser = userService.registerUser(
                    user.getEmail(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getRole()
            );

            // Mapeando para DTO para não retornar o password
            UserResponseDTO responseDTO = new UserResponseDTO(
                    createdUser.getId(),
                    createdUser.getEmail(),
                    createdUser.getUserName(),
                    createdUser.getRole()
            );

            ApiResponse<UserResponseDTO> response = new ApiResponse<>(
                    true,
                    "Usuário registrado com sucesso",
                    responseDTO
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            ApiResponse<UserResponseDTO> response = new ApiResponse<>(
                    false,
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDTO>> login(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Email e senha são obrigatórios", null));
        }

        Optional<User> authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());

        if (authenticatedUser.isPresent()) {
            User userEntity = authenticatedUser.get();

            UserResponseDTO responseDTO = new UserResponseDTO(
                    userEntity.getId(),
                    userEntity.getEmail(),
                    userEntity.getUserName(),
                    userEntity.getRole()
            );

            ApiResponse<UserResponseDTO> response = new ApiResponse<>(
                    true,
                    "Login bem-sucedido",
                    responseDTO
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Email ou senha inválidos", null));
        }
    }
}

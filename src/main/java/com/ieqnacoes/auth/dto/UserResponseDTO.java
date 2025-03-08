package com.ieqnacoes.auth.dto;

public class UserResponseDTO {
    private Long id;
    private String email;
    private String userName;
    private String role;

    public UserResponseDTO(Long id, String email, String userName, String role) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }
}

package com.example.todo.security.polo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    private String username;
    private String password;
}

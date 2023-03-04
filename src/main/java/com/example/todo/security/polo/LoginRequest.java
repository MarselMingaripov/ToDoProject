package com.example.todo.security.polo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NotBlank
public class LoginRequest {

    private String username;
    private String password;
}

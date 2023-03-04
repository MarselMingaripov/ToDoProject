package com.example.todo.security.polo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class SignupRequest {

    @NotBlank
    private String username;
    @Pattern(regexp = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20})",
            message = "Пароль должен содержать цифру, заглавную, строчную букву. Пароль должен быть больше 6 символов")
    private String password;
    @Email
    private String email;
    private Set<String> roles;
}

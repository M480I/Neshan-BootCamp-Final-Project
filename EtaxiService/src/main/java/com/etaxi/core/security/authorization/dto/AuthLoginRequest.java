package com.etaxi.core.security.authorization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthLoginRequest {

    @NotBlank(message = "username is required")
    @Email(message = "username should be an email")
    String username;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 20, message = "password should be longer than 8 characters")
    String password;

}

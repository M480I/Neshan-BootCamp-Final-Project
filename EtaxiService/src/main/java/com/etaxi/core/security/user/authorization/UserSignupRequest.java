package com.etaxi.core.security.user.authorization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSignupRequest {

    @NotBlank(message = "username is required")
    @Email(message = "username should be an email")
    String username;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 20, message = "password should be longer than 8 characters")
    String password;

}

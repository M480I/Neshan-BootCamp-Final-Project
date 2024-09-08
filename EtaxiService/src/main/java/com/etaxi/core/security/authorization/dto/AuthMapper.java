package com.etaxi.core.security.authorization.dto;

import com.etaxi.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User signupRequestToUser(AuthSignupRequest user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
    public AuthResponse userToSignupResponse(User user) {
        return AuthResponse.builder()
                .username(user.getUsername())
                .dateJoined(user.getDateJoined())
                .build();
    }
    public User loginRequestToUser(AuthLoginRequest user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

}

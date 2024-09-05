package com.etaxi.core.security.user.authorization.Dto;

import com.etaxi.core.security.user.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User signupRequestToUser(UserSignupRequest user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
    public UserResponse userToSignupResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .dateJoined(user.getDateJoined())
                .build();
    }
    public User loginRequestToUser(UserLoginRequest user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

}

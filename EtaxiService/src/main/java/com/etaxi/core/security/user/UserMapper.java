package com.etaxi.core.security.user;

import com.etaxi.core.security.user.authorization.UserLoginRequest;
import com.etaxi.core.security.user.authorization.UserSignupRequest;
import com.etaxi.core.security.user.authorization.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User signupToUser(UserSignupRequest user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
    public UserResponse userToSignup(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .build();
    }
    public User loginToUser(UserLoginRequest user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

}

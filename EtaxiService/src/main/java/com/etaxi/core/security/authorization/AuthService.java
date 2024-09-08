package com.etaxi.core.security.authorization;

import com.etaxi.core.exception.InvalidUsernameException;
import com.etaxi.core.rabbitmq.logger.LogDto;
import com.etaxi.core.rabbitmq.logger.LoggerProducer;
import com.etaxi.core.security.authorization.dto.AuthLoginRequest;
import com.etaxi.core.security.authorization.dto.AuthMapper;
import com.etaxi.core.security.authorization.dto.AuthResponse;
import com.etaxi.core.security.authorization.dto.AuthSignupRequest;
import com.etaxi.core.security.token.Jwt;
import com.etaxi.core.security.token.JwtMapper;
import com.etaxi.core.security.token.JwtResponse;
import com.etaxi.core.security.token.JwtService;
import com.etaxi.core.user.User;
import com.etaxi.core.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log
public class AuthService {

    AuthMapper authMapper;
    PasswordEncoder passwordEncoder;
    UserService userService;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    JwtMapper jwtMapper;
    LoggerProducer logger;

    public AuthResponse createUser(AuthSignupRequest authRequest)
            throws InvalidUsernameException {

        User user = authMapper.signupRequestToUser(authRequest);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.createUser(user);

        logger.storeLog(
                LogDto.builder()
                        .date(LocalDateTime.now())
                        .message(String.format("New User with Id=%d signup", user.getId()))
                        .build()
        );

        return authMapper.userToSignupResponse(user);
    }

    public JwtResponse getJwt(AuthLoginRequest authRequest)
            throws BadCredentialsException {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authRequest.getUsername(),
                                authRequest.getPassword()
                        )
                );
        if (authentication.isAuthenticated()) {
            Jwt jwt = jwtService.generateToken(authMapper.loginRequestToUser(authRequest));
            return jwtMapper.jwtToResponse(jwt);
        }

        throw new BadCredentialsException("Bad credentials");
    }
}

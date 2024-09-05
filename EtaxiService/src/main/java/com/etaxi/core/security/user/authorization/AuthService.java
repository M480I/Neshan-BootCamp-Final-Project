package com.etaxi.core.security.user.authorization;

import com.etaxi.core.exception.InvalidUsernameException;
import com.etaxi.core.security.token.Jwt;
import com.etaxi.core.security.token.JwtMapper;
import com.etaxi.core.security.token.JwtResponse;
import com.etaxi.core.security.token.JwtService;
import com.etaxi.core.security.user.User;
import com.etaxi.core.security.user.authorization.dto.AuthMapper;
import com.etaxi.core.security.user.UserRepository;
import com.etaxi.core.security.user.authorization.dto.UserLoginRequest;
import com.etaxi.core.security.user.authorization.dto.UserResponse;
import com.etaxi.core.security.user.authorization.dto.UserSignupRequest;
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


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log
public class AuthService {

    AuthMapper authMapper;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    JwtMapper jwtMapper;

    public UserResponse createUser(UserSignupRequest userRequest)
            throws InvalidUsernameException {

        User user = authMapper.signupRequestToUser(userRequest);

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
        catch (Exception exception) {
            throw new InvalidUsernameException("Username is Invalid");
        }
        return authMapper.userToSignupResponse(user);
    }

    public JwtResponse getJwt(UserLoginRequest user)
            throws BadCredentialsException {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword()
                        )
                );
        if (authentication.isAuthenticated()) {
            Jwt jwt = jwtService.generateToken(authMapper.loginRequestToUser(user));
            return jwtMapper.jwtToResponse(jwt);
        }

        throw new BadCredentialsException("Bad credentials");
    }
}

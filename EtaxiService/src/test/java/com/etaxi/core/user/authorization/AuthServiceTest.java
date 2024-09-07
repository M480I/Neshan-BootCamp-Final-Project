package com.etaxi.core.user.authorization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.etaxi.core.exception.InvalidUsernameException;
import com.etaxi.core.security.token.*;
import com.etaxi.core.security.user.User;
import com.etaxi.core.security.user.authorization.dto.AuthMapper;
import com.etaxi.core.security.user.UserRepository;
import com.etaxi.core.security.user.authorization.AuthService;
import com.etaxi.core.security.user.authorization.dto.UserLoginRequest;
import com.etaxi.core.security.user.authorization.dto.UserSignupRequest;
import com.etaxi.core.security.user.authorization.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@SpringBootTest
public class AuthServiceTest {

    @Mock
    private AuthMapper authMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private JwtMapper jwtMapper;

    @InjectMocks
    private AuthService authService;

    @Test
    void createUser_success() throws InvalidUsernameException {
        // Arrange
        UserSignupRequest userSignupRequest = new UserSignupRequest("testUser", "password");
        User user = User.builder()
                .username("testUser")
                .password("password")
                .build();

        when(authMapper.signupRequestToUser(userSignupRequest)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(authMapper.userToSignupResponse(any(User.class))).
                thenReturn(new UserResponse("testUser", LocalDateTime.now()));

        // Act
        UserResponse response = authService.createUser(userSignupRequest);

        // Assert
        assertNotNull(response);
        assertEquals(response.username(), "testUser");
        verify(authMapper).signupRequestToUser(userSignupRequest);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
        verify(authMapper).userToSignupResponse(user);
    }

    @Test
    void createUser_invalidUsernameException() {
        // Arrange
        UserSignupRequest userSignupRequest = new UserSignupRequest("testUser", "password");
        User user = User.builder()
                        .username("testUser")
                        .password("password")
                        .build();

        when(authMapper.signupRequestToUser(userSignupRequest)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        doThrow(new RuntimeException("Duplicate key")).when(userRepository).save(any(User.class));

        // Act and Assert
        assertThrows(InvalidUsernameException.class, () -> authService.createUser(userSignupRequest));
        verify(authMapper).signupRequestToUser(userSignupRequest);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
    }

    @Test
    void getJwt_success() {
        // Arrange
        UserLoginRequest loginRequest = new UserLoginRequest("testUser", "password");
        User user = User.builder()
                .username("testUser")
                .password("password")
                .build();
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Jwt jwt = new Jwt("testToken", JwtStatus.VALID, null);
        when(authMapper.loginRequestToUser(any(UserLoginRequest.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn(jwt);
        JwtResponse jwtResponse = new JwtResponse("testToken", JwtStatus.VALID.name());
        when(jwtMapper.jwtToResponse(any(Jwt.class))).thenReturn(jwtResponse);

        // Act
        JwtResponse result = authService.getJwt(loginRequest);

        // Assert
        assertNotNull(result);
        assertEquals("testToken", result.token());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(any(User.class));
        verify(jwtMapper).jwtToResponse(jwt);
    }
}

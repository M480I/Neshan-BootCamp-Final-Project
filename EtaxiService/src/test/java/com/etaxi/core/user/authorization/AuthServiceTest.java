package com.etaxi.core.user.authorization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.etaxi.core.exception.InvalidUsernameException;
import com.etaxi.core.security.token.*;
import com.etaxi.core.user.User;
import com.etaxi.core.user.UserService;
import com.etaxi.core.security.authorization.dto.AuthMapper;
import com.etaxi.core.user.UserRepository;
import com.etaxi.core.security.authorization.AuthService;
import com.etaxi.core.security.authorization.dto.AuthLoginRequest;
import com.etaxi.core.security.authorization.dto.AuthSignupRequest;
import com.etaxi.core.security.authorization.dto.AuthResponse;
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
    private UserService userService;

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
        AuthSignupRequest authSignupRequest = new AuthSignupRequest("testUser", "password");
        User user = User.builder()
                .username("testUser")
                .password("password")
                .build();

        when(authMapper.signupRequestToUser(authSignupRequest)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(authMapper.userToSignupResponse(any(User.class))).
                thenReturn(new AuthResponse("testUser", LocalDateTime.now()));

        // Act
        AuthResponse response = authService.createUser(authSignupRequest);

        // Assert
        assertNotNull(response);
        assertEquals(response.username(), "testUser");
        verify(authMapper).signupRequestToUser(authSignupRequest);
        verify(passwordEncoder).encode("password");
        verify(userService).createUser(user);
        verify(authMapper).userToSignupResponse(user);
    }

    @Test
    void createUser_invalidUsernameException() {
        // Arrange
        AuthSignupRequest authSignupRequest = new AuthSignupRequest("testUser", "password");
        User user = User.builder()
                        .username("testUser")
                        .password("password")
                        .build();

        when(authMapper.signupRequestToUser(authSignupRequest)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        doThrow(new InvalidUsernameException("Duplicate key")).when(userService).createUser(any(User.class));

        // Act and Assert
        assertThrows(InvalidUsernameException.class, () -> authService.createUser(authSignupRequest));
        verify(authMapper).signupRequestToUser(authSignupRequest);
        verify(passwordEncoder).encode("password");
        verify(userService).createUser(user);
    }

    @Test
    void getJwt_success() {
        // Arrange
        AuthLoginRequest loginRequest = new AuthLoginRequest("testUser", "password");
        User user = User.builder()
                .username("testUser")
                .password("password")
                .build();
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Jwt jwt = new Jwt("testToken", JwtStatus.VALID, null);
        when(authMapper.loginRequestToUser(any(AuthLoginRequest.class))).thenReturn(user);
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

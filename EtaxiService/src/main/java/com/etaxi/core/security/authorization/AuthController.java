package com.etaxi.core.security.authorization;

import com.etaxi.core.security.authorization.dto.AuthLoginRequest;
import com.etaxi.core.security.authorization.dto.AuthResponse;
import com.etaxi.core.security.authorization.dto.AuthSignupRequest;
import com.etaxi.core.security.token.JwtResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<AuthResponse> signup(@Valid @RequestBody AuthSignupRequest authRequest) {
        return ResponseEntity.ok(authService.createUser(authRequest));
    }

    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@Valid  @RequestBody AuthLoginRequest authRequest) {
        return ResponseEntity.ok(authService.getJwt(authRequest));
    }

}

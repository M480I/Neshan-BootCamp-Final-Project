package com.etaxi.core.security.user.authorization;

import com.etaxi.core.security.token.JwtResponse;
import com.etaxi.core.security.user.authorization.dto.UserLoginRequest;
import com.etaxi.core.security.user.authorization.dto.UserResponse;
import com.etaxi.core.security.user.authorization.dto.UserSignupRequest;
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
    ResponseEntity<UserResponse> signup(@Valid @RequestBody UserSignupRequest userRequest) {
        return ResponseEntity.ok(authService.createUser(userRequest));
    }

    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@Valid  @RequestBody UserLoginRequest userRequest) {
        return ResponseEntity.ok(authService.getJwt(userRequest));
    }

}

package org.bjing.chat.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.bjing.chat.auth.dto.AuthRequestDto;
import org.bjing.chat.auth.dto.RegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequestDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> auth(
            @RequestBody AuthRequestDto request
    ) {
        return ResponseEntity.ok(service.auth(request));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> authGithub(
            @CookieValue("JWT_TOKEN") String myToken
    ) {
        return ResponseEntity.ok(this.service.authOauthGit(myToken));
    }
}

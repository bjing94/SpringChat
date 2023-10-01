package org.bjing.chat.auth;

import lombok.RequiredArgsConstructor;
import org.bjing.chat.auth.dto.AuthRequestDto;
import org.bjing.chat.auth.dto.RegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/oauth2/github")
    public void authGithub(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "error_description", required = false) String errorDescription,
            @RequestParam(value = "code", required = false) String code
    ) {
        this.service.authOauthGit(code);
//        return ResponseEntity.ok(service.auth(request));
    }
}

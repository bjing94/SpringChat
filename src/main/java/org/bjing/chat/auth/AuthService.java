package org.bjing.chat.auth;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.bjing.chat.adapter.GithubApiService;
import org.bjing.chat.adapter.GithubAuthService;
import org.bjing.chat.adapter.dto.GithubUserResponse;
import org.bjing.chat.auth.dto.AuthRequestDto;
import org.bjing.chat.auth.dto.RegisterRequestDto;
import org.bjing.chat.config.auth.JwtService;
import org.bjing.chat.db.entity.Role;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.db.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final GithubAuthService githubAuthService;

    private final GithubApiService githubApiService;


    public AuthResponse auth(AuthRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

//        Username and password are correct
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse register(RegisterRequestDto request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)

                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse authOauthGit(String code) {
//        Authenticate using oauth2
        String token = this.githubAuthService.getAccessToken(code);
        if (token.isEmpty()) return null;

        Optional<GithubUserResponse> optionalData = this.githubApiService.getProfile(token);

        if (optionalData.isEmpty()) return null;

        GithubUserResponse userData = optionalData.get();

        Optional<User> userOptional = this.userRepository.findByEmail(userData.getLogin());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            var jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder().token(jwtToken).build();
        }

//        TODO: Custom token

        RegisterRequestDto registerRequestDto = RegisterRequestDto
                .builder()
                .email(userData.getLogin())
                .firstname("")
                .lastname("")
                .password(RandomStringUtils.random(10))
                .build();

        return this.register(registerRequestDto);
    }
}

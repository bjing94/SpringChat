package org.bjing.chat.oauth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.bjing.chat.jwt.JwtService;
import org.bjing.chat.db.entity.Role;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.db.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Oauth2AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("Auth success. Setting cookie");
        System.out.println(oAuth2User);
//        TODO: Extract attributes and type them
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("login");
        String token;
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            token = this.authenticate(optionalUser.get());
        } else {
            token = this.register(oAuth2User);
        }

//        Set cookie, retrieve later to return jwt token to user;
        Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
        jwtCookie.setSecure(false);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge((int) Duration.ofMinutes(1).toSeconds());
        response.addCookie(jwtCookie);

        response.sendRedirect("/auth/oauth2/success");
    }

    private String register(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("login");
        User user = User.builder()
                .firstname("")
                .lastname("")
                .email(email)
                .password(RandomStringUtils.random(10))
                .role(Role.USER)
                .build();
        this.userRepository.save(user);

        return this.jwtService.generateToken(user);
    }

    private String authenticate(User user) {
        return this.jwtService.generateToken(user);
    }
}

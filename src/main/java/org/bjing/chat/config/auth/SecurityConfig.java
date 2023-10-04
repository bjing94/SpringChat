package org.bjing.chat.config.auth;

import lombok.RequiredArgsConstructor;
import org.bjing.chat.config.auth.jwt.JwtAuthFilter;
import org.bjing.chat.oauth2.Oauth2AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity()
@RequiredArgsConstructor
public class SecurityConfig {
    private final Oauth2AuthService oauth2AuthService;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/auth/**", "/oauth2/**", "/login", "/login/**", "/error").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(configurer -> configurer
                        .failureHandler((request, response, exception) -> {
                            request.getSession().setAttribute("error.message", exception.getMessage());
                            System.out.println(exception.getMessage());
                        })
                        .successHandler((this.oauth2AuthService::onAuthenticationSuccess))
                )
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, OAuth2AuthorizationRequestRedirectFilter.class);
        return httpSecurity.build();
    }


}

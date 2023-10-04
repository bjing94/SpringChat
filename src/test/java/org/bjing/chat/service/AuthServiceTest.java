package org.bjing.chat.service;

import org.assertj.core.api.Assertions;
import org.bjing.chat.auth.AuthResponse;
import org.bjing.chat.auth.AuthService;
import org.bjing.chat.auth.dto.RegisterRequestDto;
import org.bjing.chat.jwt.JwtService;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;


    @Test
    public void AuthService_Register_ReturnsAuthDto() {
        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .email("test@gmail.com")
                .password("12345")
                .firstname("john")
                .lastname("smith")
                .build();
        User user = User.builder()
                .email("test@gmail.com")
                .firstname("john")
                .lastname("smith")
                .build();
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(Mockito.any(CharSequence.class))).thenReturn("12345");
        when(jwtService.generateToken(Mockito.any(UserDetails.class))).thenReturn("12345_token");

        AuthResponse response = this.authService.register(requestDto);
        Assertions.assertThat(response.getToken()).isNotNull();
    }
}

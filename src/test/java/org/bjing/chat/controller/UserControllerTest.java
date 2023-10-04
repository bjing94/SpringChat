package org.bjing.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bjing.chat.jwt.JwtService;
import org.bjing.chat.user.MyUserDetailsService;
import org.bjing.chat.user.UserController;
import org.bjing.chat.user.UserService;
import org.bjing.chat.user.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @MockBean
    private JwtService jwtService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void AuthController_Register_ReturnAuthDto() throws Exception {
        given(userService.getUser(ArgumentMatchers.any())).willAnswer(d -> UserResponse.builder().build());

        ResultActions response = mockMvc.perform(get("/users/test"));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
}

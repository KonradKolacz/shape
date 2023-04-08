package com.example.final_test.controller;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.command.UserCommand;
import com.example.final_test.jwt.JwtRequest;
import com.example.final_test.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        userRepository.deleteAll();
        UserCommand userCommand = new UserCommand("test", "test", "test", "test", "CREATOR");

        this.mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetToken() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("test", "test");
        MvcResult result = mockMvc.perform(post("/auth")
                        .content(objectMapper.writeValueAsString(jwtRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"token\":\"", "").replace("\"}", "");

        ShapeCommand shapeCommand = new ShapeCommand("SQUARE", List.of(BigDecimal.ONE));

        this.mockMvc.perform(post("/shapes").header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(shapeCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
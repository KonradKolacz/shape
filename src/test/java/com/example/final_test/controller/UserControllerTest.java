package com.example.final_test.controller;

import com.example.final_test.command.UserCommand;
import com.example.final_test.domain.User;
import com.example.final_test.exception.NoRoleTypeException;
import com.example.final_test.exception.UserExistsException;
import com.example.final_test.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldAddUser() throws Exception {
        UserCommand userCommand = new UserCommand("test", "test", "test", "test", "CREATOR");

        this.mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.login").value("test"))
                .andExpect(jsonPath("$.role").value("CREATOR"));

        assertThat(userRepository.findAll())
                .hasSize(1)
                .extracting("username").contains("test");
    }

    @Test
    public void shouldThrowExceptionWhenUserExists() throws Exception {
        User user = new User(1L, "test1", "test1", "test", "test1", "CREATOR", null);
        userRepository.save(user);

        UserCommand userCommand = new UserCommand("test", "test", "test", "test", "CREATOR");

        this.mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User with username: test exists."))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserExistsException));
    }

    @Test
    public void shouldThrowExceptionWhenLoginIsBlank() throws Exception {
        UserCommand userCommand = new UserCommand("test", "test", "", "test", "CREATOR");

        this.mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void shouldThrowExceptionWhenRoleIsNotSupported() throws Exception {
        UserCommand userCommand = new UserCommand("test", "test", "test", "test", "USER");

        this.mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Role: USER is not supported"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoRoleTypeException));
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        addUser("test", "test", "test", "test", "CREATOR");
        addUser("admin", "admin", "admin", "admin", "ADMIN");

        this.mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.size").value(5))
                .andExpect(jsonPath("$.number").value(0));
    }

    private void addUser(String name, String surname, String login, String password, String role) throws Exception {
        UserCommand userCommand = new UserCommand(name, surname, login, password, role);
        this.mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(userCommand))
                .contentType(MediaType.APPLICATION_JSON));
    }

}
package com.example.final_test.controller;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.command.ShapeUpdateCommand;
import com.example.final_test.domain.Change;
import com.example.final_test.domain.Shape;
import com.example.final_test.domain.User;
import com.example.final_test.exception.AnyChangeException;
import com.example.final_test.exception.BadAttributeNameException;
import com.example.final_test.exception.BadNumberOfParametersExceptions;
import com.example.final_test.exception.NoShapeTypeException;
import com.example.final_test.repository.ChangeRepository;
import com.example.final_test.repository.ShapeRepository;
import com.example.final_test.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShapeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ShapeRepository shapeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChangeRepository changeRepository;
    private static final String LOGIN = "test";
    private static final String PASSWORD = "test";
    private static final String ROLE_CREATOR = "CREATOR";
    private static final String ROLE_ADMIN = "ADMIN";

    @BeforeEach
    void setUp() {
        shapeRepository.deleteAll();
        userRepository.deleteAll();
        addUser(1L, "Michal", "Nowak", LOGIN, PASSWORD, ROLE_CREATOR);
        addUser(2L, "Tomek", "Wójcik", "admin", "admin", ROLE_ADMIN);
        addUser(3L, "Jacek", "Nowak", "test2", "test2", ROLE_CREATOR);
    }

    @Test
    @WithMockUser(username = LOGIN, password = PASSWORD, authorities = ROLE_CREATOR)
    public void shouldAddShape() throws Exception {
        ShapeCommand shapeCommand = new ShapeCommand("SQUARE", List.of(BigDecimal.ONE));

        this.mockMvc.perform(post("/shapes")
                        .content(objectMapper.writeValueAsString(shapeCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.createdBy").value(LOGIN))
                .andExpect(jsonPath("$.version").value(0))
                .andExpect(jsonPath("$.type").value("SQUARE"));

        List<Shape> all = shapeRepository.findAll();
        assertThat(all.get(0))
                .extracting("a").isEqualTo(BigDecimal.ONE.setScale(2, RoundingMode.CEILING));
    }

    @Test
    @WithMockUser(username = LOGIN, password = PASSWORD, authorities = ROLE_CREATOR)
    public void shouldThrowExceptionWhenTypeIsNotSupported() throws Exception {
        ShapeCommand shapeCommand = new ShapeCommand("NEW_SHAPE", List.of(BigDecimal.ONE));

        this.mockMvc.perform(post("/shapes")
                        .content(objectMapper.writeValueAsString(shapeCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ShapeType: NEW_SHAPE is not supported"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoShapeTypeException));
    }

    @Test
    @WithMockUser(username = LOGIN, password = PASSWORD, authorities = ROLE_CREATOR)
    public void shouldThrowExceptionWhenBadNumberParameters() throws Exception {
        ShapeCommand shapeCommand = new ShapeCommand("RECTANGLE", List.of(BigDecimal.ONE));

        this.mockMvc.perform(post("/shapes")
                        .content(objectMapper.writeValueAsString(shapeCommand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ShapeType: RECTANGLE should have 2 parameter(s)"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadNumberOfParametersExceptions));
    }

    @Test
    @WithMockUser(username = LOGIN, password = PASSWORD, authorities = ROLE_CREATOR)
    public void shouldGetOnlySquare() throws Exception {
        addShape("RECTANGLE", List.of(BigDecimal.valueOf(3), BigDecimal.valueOf(5)));
        addShape("CIRCLE", List.of(BigDecimal.valueOf(1)));
        addShape("SQUARE", List.of(BigDecimal.valueOf(2)));
        addShape("SQUARE", List.of(BigDecimal.valueOf(3)));

        this.mockMvc.perform(get("/shapes?type=SQUARE")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.size").value(5))
                .andExpect(jsonPath("$.number").value(0));
    }

    @Test
    @WithMockUser(username = LOGIN, password = PASSWORD, authorities = ROLE_CREATOR)
    void shouldUpdateShape() throws Exception {
        addShape("RECTANGLE", List.of(BigDecimal.valueOf(3.21), BigDecimal.valueOf(5.33)));
        Map<String, BigDecimal> valueToUpdate = new HashMap<>();
        valueToUpdate.put("a", BigDecimal.valueOf(10.02));

        Long id = shapeRepository.findAll().get(0).getId();

        this.mockMvc.perform(put("/shapes/" + id)
                        .content(objectMapper.writeValueAsString(new ShapeUpdateCommand(valueToUpdate)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.a").value(10.02))
                .andExpect(jsonPath("$.b").value(5.33));

        List<Change> changes = changeRepository.findByShapeId(id);
        assertThat(changes).hasSize(1)
                .extracting("attributeChanges").hasSize(1);
    }

    @Test
    @WithMockUser(username = LOGIN, password = PASSWORD, authorities = ROLE_CREATOR)
    void shouldThrowBadAttributeNameException() throws Exception {
        addShape("RECTANGLE", List.of(BigDecimal.valueOf(3.21), BigDecimal.valueOf(5.33)));
        Map<String, BigDecimal> valueToUpdate = new HashMap<>();
        valueToUpdate.put("r", BigDecimal.valueOf(10.02));

        Long id = shapeRepository.findAll().get(0).getId();

        this.mockMvc.perform(put("/shapes/" + id)
                        .content(objectMapper.writeValueAsString(new ShapeUpdateCommand(valueToUpdate)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ShapeType: RECTANGLE do not contain attribute r"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadAttributeNameException));

        List<Change> changes = changeRepository.findByShapeId(id);
        assertThat(changes).hasSize(0);
    }

    @Test
    @WithMockUser(username = LOGIN, password = PASSWORD, authorities = ROLE_CREATOR)
    void shouldThrowAnyChangeException() throws Exception {
        addShape("CIRCLE", List.of(BigDecimal.valueOf(10.02)));
        Map<String, BigDecimal> valueToUpdate = new HashMap<>();
        valueToUpdate.put("r", BigDecimal.valueOf(10.02));

        Long id = shapeRepository.findAll().get(0).getId();

        this.mockMvc.perform(put("/shapes/" + id)
                        .content(objectMapper.writeValueAsString(new ShapeUpdateCommand(valueToUpdate)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("All attributes to change are the same as the old values"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AnyChangeException));

        List<Change> changes = changeRepository.findByShapeId(id);
        assertThat(changes).hasSize(0);
    }

    @Test
    @WithMockUser(username = LOGIN, password = PASSWORD, authorities = ROLE_CREATOR)
    void shouldGetChanges() throws Exception {
        addShape("RECTANGLE", List.of(BigDecimal.valueOf(3.21), BigDecimal.valueOf(5.33)));
        Map<String, BigDecimal> valueToUpdate = new HashMap<>();
        valueToUpdate.put("a", BigDecimal.valueOf(10.02));

        Long id = shapeRepository.findAll().get(0).getId();

        this.mockMvc.perform(put("/shapes/" + id)
                        .content(objectMapper.writeValueAsString(new ShapeUpdateCommand(valueToUpdate)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/shapes/" + id + "/changes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private void addShape(String type, List<BigDecimal> parameters) throws Exception {
        ShapeCommand shapeCommand = new ShapeCommand(type, parameters);
        this.mockMvc.perform(post("/shapes").content(objectMapper.writeValueAsString(shapeCommand))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    private void addUser(Long id, String name, String surname, String login, String password, String role) {
        userRepository.save(new User(id, name, surname, login, password, role, new ArrayList<>()));
    }

}
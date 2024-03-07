package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class SessionControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionService sessionService;

    @Test
    @WithMockUser(username = "testProfile@test.com", roles = "ADMIN")
    public void sessionCreateTest() throws Exception {
        mockMvc.perform(get("/api/session/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Session Yoga"))
                .andExpect(jsonPath("$.description").value("Repos"));
    }

    @Test
    @WithMockUser(username = "testProfile@test.com", roles = "ADMIN")
    public void sessionGetNotFoundTest() throws Exception {
        mockMvc.perform(get("/api/session/{id}", 985321L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(username = "testProfile@test.com", roles = "ADMIN")
    public void sessionUpdateTest() throws Exception {
        String isoString = "2024-02-27T16:18:21";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(isoString, formatter);

        // Create SessionDto
        SessionDto sessionDto = new SessionDto(98L,"sessionTest",new Date(),14L, "desc Test", new ArrayList<>(),localDateTime,localDateTime);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/{id}", 98L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("sessionTest"))
                .andExpect(jsonPath("$.description").value("desc Test"));
    }

    @Test
    @WithMockUser(username = "testProfile@test.com", roles = "ADMIN")
    public void sessionUpdateBadRequestTest() throws Exception {
        String isoString = "2024-02-27T16:18:21";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(isoString, formatter);

        // Create SessionDto
        SessionDto sessionDto = new SessionDto(98L,"sessionTest",new Date(),14L, "desc Test", new ArrayList<>(),localDateTime,localDateTime);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/{id}", "dfq")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testProfile@test.com", roles = "ADMIN")
    public void sessionSaveTest() throws Exception {
        String isoString = "2024-02-27T16:18:21";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(isoString, formatter);

        // Create Session
        Session mockSession = new Session(11L,"sessionTest",new Date(),"desc Test", new Teacher(), new ArrayList<>(),localDateTime,localDateTime);

        when(sessionService.getById(11L)).thenReturn(mockSession);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}", 11L)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}

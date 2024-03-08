package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
    @Test
    @WithMockUser(username = "testProfile@test.com", roles = "ADMIN")
    public void sessionGetTest() throws Exception {
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
    public void sessionCreateTest() throws Exception {
        String isoString = "2024-02-27T16:18:21";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(isoString, formatter);
        // Create SessionDto
        SessionDto sessionDto = new SessionDto(100L,"Create test",new Date(),14L, "Create desc Test", new ArrayList<>(),localDateTime,localDateTime);


        mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Create test"))
                .andExpect(jsonPath("$.description").value("Create desc Test"));
    }
}

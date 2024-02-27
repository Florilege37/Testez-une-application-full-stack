package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    @Mock
    private SessionMapper sessionMapper;

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private SessionController sessionController;

    Session session;

    SessionDto sessionDto;

    List<Session> sessionList = new ArrayList<>();

    List<User> userList = new ArrayList<>();

    @BeforeEach
    void init(){
        sessionController = new SessionController(sessionService, sessionMapper);
        session = new Session(11L,"sessionTest",null,"desc Test", new Teacher(), userList,null,null);
        sessionList.add(session);
        sessionDto = new SessionDto(11L,"sessionTest",null,22L, "desc Test", new ArrayList<>(),null,null);
    }

    @Test
    void findByIdAndSessionNotNullTest(){
        when(sessionService.getById(Long.valueOf("11"))).thenReturn(session);

        ResponseEntity response = sessionController.findById("11");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findByIdAndSessionNullTest(){
        when(sessionService.getById(Long.valueOf("1"))).thenReturn(null);

        ResponseEntity response = sessionController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findByIdAndWrongRequestTest(){
        when(sessionService.getById(Long.valueOf("11"))).thenThrow(NumberFormatException.class);

        ResponseEntity response = sessionController.findById("11");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void findAllTest(){
        ResponseEntity response = sessionController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createTest(){
        when(sessionService.create(sessionMapper.toEntity(sessionDto))).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity response = sessionController.create(sessionDto);
        SessionDto sessionDtoBody = (SessionDto) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDtoBody.getId(),session.getId());
    }

    @Test
    void updateTest(){
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);
        when(sessionService.update(Long.parseLong("11"), sessionMapper.toEntity(sessionDto))).thenReturn(session);

        ResponseEntity response = sessionController.update("11", sessionDto);
        SessionDto sessionDtoBody = (SessionDto) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDtoBody.getId(),sessionDto.getId());
    }
    @Test
    void updateCatchErrorTest(){
        when(sessionService.update(Long.valueOf("11"),sessionMapper.toEntity(sessionDto))).thenThrow(NumberFormatException.class);

        ResponseEntity response = sessionController.update("11", sessionDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void saveTest(){
        when(sessionService.getById(Long.valueOf("11"))).thenReturn(session);

        ResponseEntity response = sessionController.save("11");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void saveNullSessionTest(){
        when(sessionService.getById(Long.valueOf("11"))).thenReturn(null);

        ResponseEntity response = sessionController.save("11");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void saveCatchErrorTest(){
        when(sessionService.getById(Long.valueOf("11"))).thenThrow(NumberFormatException.class);

        ResponseEntity response = sessionController.save("11");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void participateTest(){
        ResponseEntity response = sessionController.participate("11","22");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void participateCatchErrorTest(){
        doThrow(NumberFormatException.class)
                .when(sessionService)
                .participate(Long.parseLong("11"),Long.parseLong("22"));

        ResponseEntity response = sessionController.participate("11","22");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void noLongerParticipateTest(){
        ResponseEntity response = sessionController.noLongerParticipate("11","22");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void noLongerParticipateCatchErrorTest(){
        doThrow(NumberFormatException.class)
                .when(sessionService)
                .noLongerParticipate(Long.parseLong("11"),Long.parseLong("22"));

        ResponseEntity response = sessionController.noLongerParticipate("11","22");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

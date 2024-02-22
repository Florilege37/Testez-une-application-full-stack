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
        assertThrows(NumberFormatException.class,() -> sessionService.getById(Long.valueOf("Test")));
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
        assertThrows(NumberFormatException.class,() -> sessionService.update(Long.valueOf("Test"),sessionMapper.toEntity(sessionDto)));
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
        assertThrows(NumberFormatException.class,() -> sessionService.getById(Long.valueOf("Test")));
    }

    @Test
    void participateTest(){
        ResponseEntity response = sessionController.participate("11","22");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void participateCatchErrorTest(){
        assertThrows(NumberFormatException.class,() -> sessionService.participate(Long.valueOf("test"),Long.valueOf("test2")));
    }

    @Test
    void noLongerParticipateTest(){
        ResponseEntity response = sessionController.noLongerParticipate("11","22");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void noLongerParticipateCatchErrorTest(){
        assertThrows(NumberFormatException.class,() -> sessionService.noLongerParticipate(Long.valueOf("test"),Long.valueOf("test2")));
    }
}

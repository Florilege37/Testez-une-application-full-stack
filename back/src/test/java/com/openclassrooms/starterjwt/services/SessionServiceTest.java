package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;
    private SessionService sessionService;

    private List<Session> sessionsList = new ArrayList<>();

    Session mockSession = new Session();

    User mockUser = new User();

    Long sessionId = 21L;

    Long userId = 55L;

    @BeforeEach
    public void init(){
        mockSession.setId(sessionId);
        mockUser.setId(userId);
        mockSession.setUsers(new ArrayList<>());
        sessionService = new SessionService(sessionRepository, userRepository);
        Session session1 = new Session();
        session1.setId(1L);
        Session session2 = new Session();
        session2.setId(2L);
        Session session3 = new Session();
        session3.setId(3L);

        sessionsList.add(session1);
        sessionsList.add(session2);
        sessionsList.add(session3);
    }

    @Test
    public void isDeleteCalled(){
        sessionService.delete(sessionId);
        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @Test
    public void isCreateCalledAndReturnSession(){
        when(sessionRepository.save(mockSession)).thenReturn(mockSession);
        Session sessionResult = sessionService.create(mockSession);

        verify(sessionRepository, times(1)).save(mockSession);
        assertEquals(sessionResult.getId(),mockSession.getId());
    }

    @Test
    public void isFindAllCalledAndReturnAllSessions(){
        when(sessionRepository.findAll()).thenReturn(sessionsList);

        List<Session> SessionListResult = sessionRepository.findAll();

        assertEquals(SessionListResult.size(),sessionsList.size());
        assertEquals(SessionListResult.get(0).getId(),sessionsList.get(0).getId());

        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    public void isFindSessionCalledAndFindSession(){
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.ofNullable(mockSession));
        Session session = sessionService.getById(sessionId);

        assertNotNull(session);
        assertEquals(sessionId, session.getId());
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void isFindSessionCalledAndReturnNull(){
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertNull(sessionService.getById(sessionId));
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void isUpdateCalledAndUpdateSession(){

        when(sessionRepository.save(mockSession)).thenReturn(mockSession);

        Session session = sessionService.update(24L, mockSession);

        verify(sessionRepository, times(1)).save(mockSession);
        assertEquals(24L, session.getId());
    }

    @Test
    public void userParticipateToSession(){
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(mockUser));
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.ofNullable(mockSession));
        when(sessionRepository.save(mockSession)).thenReturn(mockSession);

        sessionService.participate(sessionId, userId);

        verify(sessionRepository, times(1)).findById(sessionId);
        verify(userRepository, times(1)).findById(userId);
        verify(sessionRepository, times(1)).save(mockSession);
        assertEquals(mockSession.getUsers().get(0).getId(),mockUser.getId());
    }

    @Test
    public void userParticipateToSessionButUserNull(){
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.ofNullable(mockSession));

        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(userRepository, times(1)).findById(userId);

    }

    @Test
    public void userParticipateToSessionButSessionNull(){
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(mockUser));
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void userParticipateToSessionButUserAlreadyRegistered(){
        mockSession.getUsers().add(mockUser);

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(mockUser));
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.ofNullable(mockSession));

        assertThrows(BadRequestException.class, () -> sessionService.participate(sessionId, userId));
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void userNoLongerParticipateToSession(){
        mockSession.getUsers().add(mockUser);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.ofNullable(mockSession));
        when(sessionRepository.save(mockSession)).thenReturn(mockSession);

        sessionService.noLongerParticipate(sessionId, userId);

        verify(sessionRepository, times(1)).findById(sessionId);
        verify(sessionRepository, times(1)).save(mockSession);
        assertFalse(mockSession.getUsers().contains(mockUser));
    }

    @Test
    public void userNoLongerParticipateToSessionButSessionNull(){
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void userNoLongerParticipateToSessionButUserNotRegistered(){
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.ofNullable(mockSession));

        assertThrows(BadRequestException.class, () -> sessionService.participate(sessionId, userId));
        verify(sessionRepository, times(1)).findById(sessionId);
    }
}

package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService userService;

    User mockUser = new User();
    Long userId;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        userId = 999L;
        mockUser.setId(userId);
        userService = new UserService(userRepository);
    }
    @Test
    public void isDeleteCalled(){
        userService.delete(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void isFindUserCalledAndFindUser(){
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(mockUser));
        User user = userService.findById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void isFindUserCalledAndReturnNull(){
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertNull(userService.findById(userId));
        verify(userRepository, times(1)).findById(userId);
    }
}

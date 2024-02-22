package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserService userService;

    private UserController userController;

    private User mockUser;

    private UserDto mockUserDto;

    private UserDetails userDetails;

    @BeforeEach
    void init() {
        userController = new UserController(userService, userMapper);
        mockUser = new User(55L, "mail@test", "Blanc", "Michel", "pwdTest", false, null, null);
        mockUserDto = new UserDto(55L, "mail@test", "Blanc", "Michel", false, "pwdTest", null, null);
    }

    @Test
    void findByIdCatchErrorTest() {
        assertThrows(NumberFormatException.class, () -> userService.findById(Long.valueOf("test")));
    }

    @Test
    void findByIdTeacherNullTest() {
        when(userService.findById(Long.valueOf("55"))).thenReturn(null);

        ResponseEntity response = userController.findById("55");

        verify(userService,times(1)).findById(Long.parseLong("55"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findByIdTest() {
        when(userService.findById(Long.valueOf("55"))).thenReturn(mockUser);
        when(userMapper.toDto(mockUser)).thenReturn(mockUserDto);

        ResponseEntity response = userController.findById("55");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser.getId(), mockUser.getId());
    }

    @Test
    void saveNullUserTest() {
        when(userService.findById(Long.valueOf("55"))).thenReturn(null);

        ResponseEntity response = userController.save("55");

        verify(userService,times(1)).findById(Long.parseLong("55"));
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void saveCatchErrorTest() {
        assertThrows(NumberFormatException.class, () -> userService.findById(Long.valueOf("Test")));
    }

    @Test
    void saveUnauthorizedTest() {
        userDetails = new UserDetailsImpl(55L,"mail@testWRONG","Michel","Blanc",false,"pwd");
        when(userService.findById(Long.valueOf("55"))).thenReturn(mockUser);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);

        ResponseEntity response = userController.save("55");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void saveTest() {
        userDetails = new UserDetailsImpl(55L,"mail@test","Michel","Blanc",false,"pwd");
        when(userService.findById(Long.valueOf("55"))).thenReturn(mockUser);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);

        ResponseEntity response = userController.save("55");

        verify(userService,times(1)).findById(Long.parseLong("55"));
        verify(userService,times(1)).delete(Long.parseLong("55"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}


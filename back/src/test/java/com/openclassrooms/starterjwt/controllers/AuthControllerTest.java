package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    private AuthController authController;

    User mockUser;

    UserDetailsImpl mockUserDetails;

    @BeforeEach
    void init(){
        mockUser = new User(1L, "email@test", "Michel", "Blanc", "pwdTest",false,null,null);
        mockUserDetails = new UserDetailsImpl(1L,"email@test","Michel","Blanc",false,"pwdTest");
        authController = new AuthController(authenticationManager,passwordEncoder,jwtUtils,userRepository);
    }

    @Test
    void authenticateUserTestReturnGoodUser(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email@test");
        loginRequest.setPassword("pwdTest");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("token");
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);

        when(userRepository.findByEmail(mockUserDetails.getUsername())).thenReturn(Optional.of(mockUser));

        ResponseEntity response = authController.authenticateUser(loginRequest);
        JwtResponse jwtResponse = (JwtResponse) response.getBody();

        assertEquals(jwtResponse.getId(), mockUserDetails.getId());
    }

    @Test
    void registerUserTestReturnGoodUser(){

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@gmail");
        signupRequest.setPassword("pwdTest");
        signupRequest.setFirstName("Michel");
        signupRequest.setLastName("Blanc");

        when(userRepository.existsByEmail("test@gmail")).thenReturn(false);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("pwdTestEncode");

        User user = new User(signupRequest.getEmail(),
                signupRequest.getLastName(),
                signupRequest.getFirstName(),
                passwordEncoder.encode(signupRequest.getPassword()),
                false);

        verify(userRepository, times(1)).save(user);
    }
}

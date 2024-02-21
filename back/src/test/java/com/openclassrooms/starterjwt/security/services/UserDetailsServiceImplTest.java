package com.openclassrooms.starterjwt.security.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;
    User mockUser = new User();

    long userId;
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void init(){
        userId = 999L;
        mockUser.setId(userId);
        mockUser.setEmail("MichelMail");
        mockUser.setLastName("Blanc");
        mockUser.setFirstName("Michel");
        mockUser.setPassword("MichelPwd");
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsernameTest(){
        String username = "Michel";
        when(userRepository.findByEmail(username)).thenReturn(Optional.ofNullable(mockUser));

        UserDetails userDetailsRetour = userDetailsService.loadUserByUsername(username);
        UserDetails userMockComparaison = UserDetailsImpl
                .builder()
                .id(mockUser.getId())
                .username(mockUser.getEmail())
                .lastName(mockUser.getLastName())
                .firstName(mockUser.getFirstName())
                .password(mockUser.getPassword())
                .build();
        assertEquals(userDetailsRetour.getUsername(),userMockComparaison.getUsername());
    }

    @Test
    void loadUserByUsernameTest_NotFound(){
        String username = "Michel";
        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }
}

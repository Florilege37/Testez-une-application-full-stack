package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserDetailsImplTest {

    UserDetailsImpl userDetails;

    @BeforeEach
    void init(){
        userDetails = new UserDetailsImpl(1L,"Michel","name", "lastname", false, "pwd");
    }

    @Test
    void isAccountNonExpiredReturnTrue(){
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLockedReturnTrue(){
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpiredReturnTrue(){
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabledReturnTrue(){
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void isEqualsReturnTrue(){
        UserDetailsImpl userDetailsToCompare =  new UserDetailsImpl(1L,"Michel","name", "lastname", false, "pwd");
        assertTrue(userDetails.equals(userDetailsToCompare));
    }

    @Test
    void isEqualsReturnFalse(){
        UserDetailsImpl userDetailsToCompare =  new UserDetailsImpl(2L,"Michel","name", "lastname", false, "pwd");
        assertFalse(userDetails.equals(userDetailsToCompare));
    }

    @Test
    void isEqualsReturnFalseCauseDifferentClass(){
        String compare = "comparaison";
        assertFalse(userDetails.equals(compare));
    }
}

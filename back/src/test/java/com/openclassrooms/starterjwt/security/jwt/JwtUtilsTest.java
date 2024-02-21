package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @Mock
    private Authentication authentication;
    @Mock
    private UserDetailsImpl userDetails;
    @InjectMocks
    private JwtUtils jwtUtils;
    private String jwtSecret;
    private int jwtExpirationMs;

    @BeforeEach
    void init(){
        jwtSecret = "openclassrooms";
        jwtExpirationMs = 3600000;
        jwtUtils = new JwtUtils();
        userDetails = new UserDetailsImpl(1L,"UserTest", "nameTest", "lastTest", false, "pwdTest");
    }

    @Test
    void generateGwtToken(){
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String token = jwtUtils.generateJwtToken(authentication);
        assertNotNull(token);
    }

    @Test
    void returnUsernameFromToken(){
       when(authentication.getPrincipal()).thenReturn(userDetails);
       String token = jwtUtils.generateJwtToken(authentication);
       assertEquals(jwtUtils.getUserNameFromJwtToken(token),userDetails.getUsername());
    }

    @Test
    void validateTokenAndReturnTrue(){
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String token = jwtUtils.generateJwtToken(authentication);
        boolean result = jwtUtils.validateJwtToken(token);
        assertTrue(result);
    }

    @Test
    void validateTokenButInvalidSignature(){
        when(authentication.getPrincipal()).thenReturn(userDetails);
        JwtUtils jwtUtilsMock = Mockito.mock(JwtUtils.class);
        String token = jwtUtils.generateJwtToken(authentication) + "wrongSignature";
        doThrow(new SignatureException("")).when(jwtUtilsMock).validateJwtToken(token);
        jwtUtils.validateJwtToken(token);

        assertFalse(jwtUtils.validateJwtToken(token));
        assertThrows(SignatureException.class, () -> jwtUtilsMock.validateJwtToken(token));
    }

    @Test
    void validateTokenButMalformedJwt(){
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String token = jwtUtils.generateJwtToken(authentication);

        JwtUtils jwtUtilsMock = Mockito.mock(JwtUtils.class);
        doThrow(new MalformedJwtException("")).when(jwtUtilsMock).validateJwtToken(token);
        assertThrows(MalformedJwtException.class, () -> jwtUtilsMock.validateJwtToken(token));
        assertFalse(jwtUtils.validateJwtToken(token + "."));
    }

    @Test
    void validateTokenButExpiredJWT(){
        when(authentication.getPrincipal()).thenReturn(userDetails);
        this.jwtExpirationMs = -60000;
        String token = jwtUtils.generateJwtToken(authentication);
        JwtUtils jwtUtilsMock = Mockito.mock(JwtUtils.class);
        doThrow(new ExpiredJwtException(null,null,null)).when(jwtUtilsMock).validateJwtToken(token);

        assertThrows(ExpiredJwtException.class, () -> jwtUtilsMock.validateJwtToken(token));
        assertFalse(jwtUtils.validateJwtToken(token+"."));
    }

    @Test
    void validateTokenButUnsupportedJWT(){
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String token = jwtUtils.generateJwtToken(authentication);
        JwtUtils jwtUtilsMock = Mockito.mock(JwtUtils.class);
        doThrow(new UnsupportedJwtException("")).when(jwtUtilsMock).validateJwtToken(token);

        assertThrows(UnsupportedJwtException.class, () -> jwtUtilsMock.validateJwtToken(token));
    }

    @Test
    void validateTokenButIllegalArgumentJWT(){
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String token = jwtUtils.generateJwtToken(authentication);

        JwtUtils jwtUtilsMock = Mockito.mock(JwtUtils.class);
        doThrow(new IllegalArgumentException("")).when(jwtUtilsMock).validateJwtToken(token);

        assertThrows(IllegalArgumentException.class, () -> jwtUtilsMock.validateJwtToken(token));
    }
}

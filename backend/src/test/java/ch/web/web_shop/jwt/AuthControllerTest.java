package ch.web.web_shop.jwt;

import ch.web.web_shop.controller.AuthController;
import ch.web.web_shop.payload.request.LoginRequest;
import ch.web.web_shop.payload.response.JwtResponse;
import ch.web.web_shop.repository.UserRepository;
import ch.web.web_shop.security.jwt.JwtUtils;
import ch.web.web_shop.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class AuthControllerTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder encoder;

    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authController = new AuthController(authenticationManager, encoder, jwtUtils);
    }


    @Test
    public void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpassword");

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        String jwt = "mocked-jwt-token";
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwt);

        // Mock the authenticationManager to return the authentication object
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        assertEquals(ResponseEntity.ok().build().getClass(), response.getClass());
        assertEquals(JwtResponse.class, response.getBody().getClass());
    }





}

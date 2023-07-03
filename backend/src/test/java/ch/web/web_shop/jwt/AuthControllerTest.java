package ch.web.web_shop.jwt;

import ch.web.web_shop.controller.AuthController;

import ch.web.web_shop.model.ERole;
import ch.web.web_shop.model.Role;
import ch.web.web_shop.payload.request.LoginRequest;

import ch.web.web_shop.payload.request.SignupRequest;
import ch.web.web_shop.payload.response.JwtResponse;

import ch.web.web_shop.payload.response.MessageResponse;
import ch.web.web_shop.repository.RoleRepository;
import ch.web.web_shop.repository.UserRepository;
import ch.web.web_shop.security.jwt.JwtUtils;
import ch.web.web_shop.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import static org.mockito.Mockito.*;

public class AuthControllerTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAuthenticateUser() {
        // Erstelle eine LoginRequest mit Testbenutzerdaten
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpassword");

        // Mocke die Authentication und setze sie im SecurityContextHolder
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Mocke UserDetailsImpl
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        // Setze den Mock-JWT-Token
        String jwt = "mocked-jwt-token";
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwt);

        // Mocke den authenticationManager, um das Authentication-Objekt zurückzugeben
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Rufe die authenticateUser-Methode auf dem authController auf
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        // Überprüfe, ob die Response den erwarteten Klassen entspricht
        assertEquals(ResponseEntity.ok().build().getClass(), response.getClass());
        assertEquals(JwtResponse.class, response.getBody().getClass());
    }


    @Test
    public void testRegisterUser() {
       // Mock request data
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testUser");
        signupRequest.setEmail("testEmail");
        signupRequest.setPassword("testPassword");

        // Mock role data
        Set<String> strRoles = new HashSet<>();
        strRoles.add("user");
        signupRequest.setRole(strRoles);

        // Mock role repository
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(ERole.ROLE_USER)));

        // Perform the registration
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Verify the response
        assertEquals(200, responseEntity.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertEquals("User registered successfully!", messageResponse.getMessage());

        // Verify that the user is saved
        verify(userRepository, times(1)).save(any());
    }



}

package com.assignment.task_manager;


import com.assignment.task_manager.entity.User;
import com.assignment.task_manager.repo.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@ContextConfiguration need to implement
@SpringBootTest(classes = TaskManagerApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public abstract class AbstractTest {

    @Autowired
    public MockMvc mvc;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks

        // Mock SecurityContext and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Jwt jwt = mock(Jwt.class); // Mock the Jwt object

        // Set up the Jwt with a claim for the email
        when(jwt.getClaimAsString("email")).thenReturn("test@example.com");

        // Set up the SecurityContext with mocked Authentication
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt); // Return the Jwt as the principal
        Jwt jwt1 = new Jwt("token", Instant.now(), Instant.now().plusSeconds(3600), Collections.singletonMap("alg", "none"), Collections.singletonMap("sub", "test@example.com"));
        JwtAuthenticationToken jwtAuth = new JwtAuthenticationToken(jwt1, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(jwtAuth);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Set the SecurityContextHolder to use the mocked context
        SecurityContextHolder.setContext(securityContext);
        String email = "test@example.com";
        String userId = "12345";
        User user = new User();
        user.setUserId(userId);
        user.setEmail(email);


        when(userRepository.findByEmail(email)).thenReturn(user);
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}
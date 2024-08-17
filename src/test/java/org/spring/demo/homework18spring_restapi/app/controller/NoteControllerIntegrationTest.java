package org.spring.demo.homework18spring_restapi.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.demo.homework18spring_restapi.app.model.Note;
import org.spring.demo.homework18spring_restapi.app.model.Role;
import org.spring.demo.homework18spring_restapi.app.model.User;
import org.spring.demo.homework18spring_restapi.app.repository.NoteRepository;
import org.spring.demo.homework18spring_restapi.app.repository.RoleRepository;
import org.spring.demo.homework18spring_restapi.app.repository.UserRepository;
import org.spring.demo.homework18spring_restapi.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
//@Import(TestcontainersConfiguration.class)
class NoteControllerIntegrationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private NoteRepository noteRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.0");

    @BeforeEach
    void setUp() {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        User user = new User();
        user.setUsername("testUser");
        user.setPassword(passwordEncoder.encode("testPassword"));
        user.addRole(userRole);
        userRepository.save(user);

        Note note = Note.builder()
                .title("Test Note")
                .content("This is a test note")
                .user(user)
                .build();
        noteRepository.save(note);
    }


    @Test
    void createNote_ReturnsResponseEntity() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");
        String jwtToken = jwtUtil.generateToken(userDetails);
        String noteJson = "{ \"title\": \"Test Note\", \"content\": \"This is a test note.\" }";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notes")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noteJson)
        )
                .andExpectAll(
                        status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    void getNoteById_ReturnsResponseEntity() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notes/1")
                .header("Authorization", "Bearer " +
                        jwtUtil.generateToken(userDetailsService.loadUserByUsername("testUser")))
        )
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                );
    }
}
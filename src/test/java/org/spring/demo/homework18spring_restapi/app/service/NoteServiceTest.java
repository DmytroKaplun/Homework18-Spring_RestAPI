package org.spring.demo.homework18spring_restapi.app.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.demo.homework18spring_restapi.app.model.Note;
import org.spring.demo.homework18spring_restapi.app.model.User;
import org.spring.demo.homework18spring_restapi.app.model.dto.NoteRequest;
import org.spring.demo.homework18spring_restapi.app.model.dto.NoteResponse;
import org.spring.demo.homework18spring_restapi.app.repository.NoteRepository;
import org.spring.demo.homework18spring_restapi.app.repository.UserRepository;
import org.spring.demo.homework18spring_restapi.app.util.NoteMapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @Spy
    private NoteMapperImpl noteMapper;
    @InjectMocks
    private NoteService noteService;

    @Test
    void AddNote_ReturnsValidResponseString() {
        NoteRequest noteRequest = NoteRequest.builder()
                .title("Sample Title")
                .content("Sample Content").build();

        User mockUser = new User();
        mockUser.setUsername("testUser");

        Note mockNote = new Note();
        mockNote.setTitle("Sample Title");
        mockNote.setContent("Sample Content");
        mockNote.setUser(mockUser);

        when(authentication.getName()).thenReturn("testUser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(mockUser));
        when(noteMapper.toEntity(noteRequest)).thenReturn(mockNote);
        when(noteRepository.save(mockNote)).thenReturn(mockNote);

        String response = noteService.addNote(noteRequest);

        assertEquals("Note has been saved successfully for user: testUser", response);
        verify(userRepository).findByUsername("testUser");
        verify(noteMapper).toEntity(noteRequest);
        verify(noteRepository).save(mockNote);
    }

    @Test
    void getNoteById_ReturnsValidNoteResponse() {
        Long noteId = 1L;
        Note note = Note.builder()
                .title("Sample Title")
                .content("Sample Content").build();
        NoteResponse respExpected = NoteResponse.builder()
                .title("Sample Title")
                .content("Sample Content").build();

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        NoteResponse actualResp = noteService.getNoteById(noteId);

        Assertions.assertEquals(respExpected, actualResp);
        Assertions.assertEquals("Sample Title", actualResp.getTitle());
        Assertions.assertEquals("Sample Content", actualResp.getContent());
        verify(noteRepository).findById(noteId);
    }
}
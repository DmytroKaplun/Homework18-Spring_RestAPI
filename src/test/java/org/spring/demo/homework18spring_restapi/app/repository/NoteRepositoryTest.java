package org.spring.demo.homework18spring_restapi.app.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.spring.demo.homework18spring_restapi.app.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @Test
    void shouldSaveNote() {
        Note note = Note.builder()
                .title("Test title")
                .content("Test content")
                .build();
        Note savedNote = noteRepository.save(note);

        Assertions.assertNotNull(savedNote);
        Assertions.assertEquals(note.getTitle(), savedNote.getTitle());
    }

    @Test
    void shouldFindAllNotes() {
        Note note1 = Note.builder()
                .title("Test 1 title")
                .content("Test 1 content").build();
        Note note2 = Note.builder()
                .title("Test 2 title")
                .content("Test 2 content").build();

        noteRepository.save(note1);
        noteRepository.save(note2);

        List<Note> notes = noteRepository.findAll();
        Assertions.assertNotNull(notes);
        Assertions.assertEquals(2, notes.size());
    }

    @Test
    void testFindNoteById_Success() {
        Note noteToFind = Note.builder()
                .title("Test 1 title")
                .content("Test 1 content").build();

        noteRepository.save(noteToFind);
        Note noteReturned = noteRepository.findById(noteToFind.getId()).orElse(null);

        Assertions.assertNotNull(noteReturned);
        Assertions.assertEquals(noteReturned.getTitle(), noteToFind.getTitle());

    }
}
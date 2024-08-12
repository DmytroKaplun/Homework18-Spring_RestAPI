package org.spring.demo.homework18spring_restapi.app.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.spring.demo.homework18spring_restapi.app.repository.NoteRepository;
import org.spring.demo.homework18spring_restapi.app.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final NoteService noteService;
    private final NoteRepository noteRepository;

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException("Note not found with id " + id);
        }
        noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

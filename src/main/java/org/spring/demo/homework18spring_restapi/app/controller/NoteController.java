package org.spring.demo.homework18spring_restapi.app.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.spring.demo.homework18spring_restapi.app.model.dto.NoteRequest;
import org.spring.demo.homework18spring_restapi.app.model.dto.NoteResponse;
import org.spring.demo.homework18spring_restapi.app.service.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<String> createNote(@RequestBody NoteRequest noteRequest) {
        return noteService.addNote(noteRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @PutMapping("/{id}")
    public NoteResponse updateNote(@PathVariable Long id,
                                   @RequestBody NoteRequest noteRequest) {
        return noteService.updateNote(id, noteRequest);
    }

    @GetMapping("/findAll")
    public Page<NoteResponse> findAllNotes(@RequestParam(defaultValue = "0") int offset,
                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(offset, size);
        return noteService.findAllNotes(pageable);
    }

    @GetMapping("/findAll/{title}")
    public List<NoteResponse> getNoteByTitle(@PathVariable String title) {
        return noteService.getNotesByTitle(title);
    }
}

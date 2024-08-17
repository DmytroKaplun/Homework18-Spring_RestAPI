package org.spring.demo.homework18spring_restapi.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.spring.demo.homework18spring_restapi.app.model.dto.NoteRequest;
import org.spring.demo.homework18spring_restapi.app.model.dto.NoteResponse;
import org.spring.demo.homework18spring_restapi.app.service.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
@Tags(value = {
        @Tag(name = "Note controller", description = "Provides basic note management")
})
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    @Operation(summary = "Creates a new note", responses = {
            @ApiResponse(description = "CREATED", responseCode = "201"),
            @ApiResponse(description = "NOT_FOUND", responseCode = "404")
    })
    public ResponseEntity<String> createNote(@RequestBody NoteRequest noteRequest) {
        String addedNote = noteService.addNote(noteRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(addedNote);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        NoteResponse noteById = noteService.getNoteById(id);
        return ResponseEntity.ok(noteById);
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

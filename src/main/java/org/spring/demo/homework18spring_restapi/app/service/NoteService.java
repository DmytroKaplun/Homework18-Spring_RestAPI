package org.spring.demo.homework18spring_restapi.app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.spring.demo.homework18spring_restapi.app.model.Note;
import org.spring.demo.homework18spring_restapi.app.model.User;
import org.spring.demo.homework18spring_restapi.app.model.dto.NoteRequest;
import org.spring.demo.homework18spring_restapi.app.model.dto.NoteResponse;
import org.spring.demo.homework18spring_restapi.app.repository.NoteRepository;
import org.spring.demo.homework18spring_restapi.app.repository.UserRepository;
import org.spring.demo.homework18spring_restapi.app.util.NoteMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteMapper noteMapper;

    public String addNote(NoteRequest noteRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with username " + username));

        Note note = noteMapper.toEntity(noteRequest);
        note.setUser(user);

        noteRepository.save(note);
        return "Note has been saved successfully for user: " + username;
    }

    public NoteResponse getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id " + id));
        return noteMapper.toNoteResponse(note);
    }

    @Transactional
    public NoteResponse updateNote(Long id, NoteRequest noteRequest) {
        var note = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id " + id));
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        return noteMapper.toNoteResponse(note);
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }

    public Page<NoteResponse> findAllNotes(Pageable pageable) {
        Page<Note> notePages = noteRepository.findAll(pageable);
        List<NoteResponse> noteResponses = notePages.stream()
                .map(noteMapper::toNoteResponse)
                .toList();
        return new PageImpl<>(noteResponses, pageable, notePages.getTotalElements());
    }

    public List<NoteResponse> getNotesByTitle(String title) {
        return noteRepository.findAll(getNoteByTitle(title)).stream()
                .map(noteMapper::toNoteResponse)
                .toList();
    }

    private Specification<Note> getNoteByTitle(String title) {
        return (root, query, criteriaBuilder) -> Optional.ofNullable(title)
                .map(t -> criteriaBuilder.equal(root.get("title"), title))
                .orElseGet(criteriaBuilder::conjunction);
    }
}

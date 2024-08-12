package org.spring.demo.homework18spring_restapi.app.repository;

import org.spring.demo.homework18spring_restapi.app.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
}

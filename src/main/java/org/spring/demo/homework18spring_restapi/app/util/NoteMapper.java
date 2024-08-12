package org.spring.demo.homework18spring_restapi.app.util;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.spring.demo.homework18spring_restapi.app.model.Note;
import org.spring.demo.homework18spring_restapi.app.model.dto.NoteRequest;
import org.spring.demo.homework18spring_restapi.app.model.dto.NoteResponse;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NoteMapper {
    NoteResponse toNoteResponse(Note note);

    Note toEntity(NoteRequest noteRequest);
}

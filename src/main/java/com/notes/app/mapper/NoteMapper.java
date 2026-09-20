package com.notes.app.mapper;

import com.notes.app.dto.NoteResponse;
import com.notes.app.model.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    public NoteResponse toResponse(Note note) {

        return new NoteResponse (
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }
}

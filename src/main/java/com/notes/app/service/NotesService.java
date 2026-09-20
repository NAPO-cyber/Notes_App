package com.notes.app.service;

import com.notes.app.dto.NoteRequest;
import com.notes.app.dto.NoteResponse;
import com.notes.app.exception.NoteNotFoundException;
import com.notes.app.model.Note;
import com.notes.app.repository.NotesRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class NotesService {

    private final NotesRepo notesRepo;

    public NotesService(NotesRepo notesRepo) {
        this.notesRepo = notesRepo;
    }

    public NoteResponse createNote(NoteRequest request) {
        Note note = new Note();

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        Note savedNote = notesRepo.save(note);

        return new NoteResponse(
                savedNote.getId(),
                savedNote.getTitle(),
                savedNote.getContent(),
                savedNote.getCreatedAt(),
                savedNote.getUpdatedAt()
        );
    }

    public Page<NoteResponse> getAllNotes(Pageable pageable) {
        Page<NoteResponse> note = notesRepo.findAll(pageable)
                .map(note1 -> new NoteResponse(
                        note1.getId(),
                        note1.getTitle(),
                        note1.getContent(),
                        note1.getCreatedAt(),
                        note1.getUpdatedAt()
                ));

        if (note.isEmpty()) {
            throw new NoteNotFoundException("there are no notes yet!");
        }

        return note;
    }

    public Note getNoteById(Long id) {
        return notesRepo.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
    }

    public void deleteAll() {
        notesRepo.deleteAll();
    }

    public void deleteById(Long id) {
        Note note = getNoteById(id);

        notesRepo.delete(note);
    }

    public NoteResponse updateNote(Long id, NoteRequest request) {
        Note note = notesRepo.findById(id)
                        .orElseThrow(() -> new NoteNotFoundException("Note not found!"));

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        Note updateeNote = notesRepo.save(note);

        return new NoteResponse(
                updateeNote.getId(),
                updateeNote.getTitle(),
                updateeNote.getContent(),
                updateeNote.getCreatedAt(),
                updateeNote.getUpdatedAt()
        );
    }
}

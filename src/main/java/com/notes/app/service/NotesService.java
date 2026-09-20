package com.notes.app.service;

import com.notes.app.dto.NoteRequest;
import com.notes.app.exception.NoteNotFoundException;
import com.notes.app.model.Note;
import com.notes.app.repository.NotesRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private final NotesRepo notesRepo;

    public NotesService(NotesRepo notesRepo) {
        this.notesRepo = notesRepo;
    }

    public Note createNote(NoteRequest request) {
        Note note = new Note();

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        return notesRepo.save(note);
    }

    public Page<Note> getAllNotes(Pageable pageable) {
        Page<Note> note = notesRepo.findAll(pageable);

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

    public Note updateNote(Long id, NoteRequest request) {
        Note note = getNoteById(id);

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        return notesRepo.save(note);
    }
}

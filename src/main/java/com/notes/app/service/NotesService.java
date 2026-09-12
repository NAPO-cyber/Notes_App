package com.notes.app.service;

import com.notes.app.dto.NoteRequest;
import com.notes.app.exception.NoteNotFoundException;
import com.notes.app.model.Note;
import com.notes.app.repository.NotesRepo;
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

    public List<Note> getAllNotes() {
        List<Note> note = notesRepo.findAll();

        if (note.isEmpty()) {

            throw new NoteNotFoundException("there are no notes yet!");
        }
        return note;
    }

    public Note getNoteById(Long id) {
        Note note = notesRepo.findById(id);

        if (note == null) {
            throw new NoteNotFoundException("note not found with id: " + id);
        }
        return note;
    }

    public void deleteAll() {
        notesRepo.clearAll();
    }

    public void deleteById(Long id) {
        notesRepo.clearById(id);
    }

    public Note updateNote(Long id, NoteRequest request) {

        Note existingNote = notesRepo.findById(id);

        if (existingNote == null) {
            throw new NoteNotFoundException(
                    "Note not found with id: " + id
            );
        }

        existingNote.setTitle(request.getTitle());
        existingNote.setContent(request.getContent());

        return notesRepo.update(existingNote);
    }
}

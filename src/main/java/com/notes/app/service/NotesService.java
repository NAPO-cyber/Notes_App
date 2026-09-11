package com.notes.app.service;

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

    public Note createNote(Note note) {
        return notesRepo.save(note);
    }

    public List<Note> getAllNotes() {
        return notesRepo.findAll();
    }

    public Note getNoteById(Long id) {
        return notesRepo.FindById(id);
    }

    public void deleteAll() {
        notesRepo.clearAll();
    }

    public void deleteById(Long id) {
        notesRepo.clearById(id);
    }

}

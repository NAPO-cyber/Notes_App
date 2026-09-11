package com.notes.app.controller;

import com.notes.app.model.Note;
import com.notes.app.service.NotesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    // create
    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return notesService.createNote(note);
    }

    // read
    @GetMapping
    public List<Note> getNotes() {
        return notesService.getAllNotes();
    }

    // read one note
    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return notesService.getNoteById(id);
    }

    // delete all
    @DeleteMapping
    public void deleteAll() {
        notesService.deleteAll();
    }

    // delete by id
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        notesService.deleteById(id);
    }



}

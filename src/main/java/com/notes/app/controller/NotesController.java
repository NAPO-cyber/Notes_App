package com.notes.app.controller;

import com.notes.app.model.Note;
import com.notes.app.service.NotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note createdNote = notesService.createNote(note);

    // an XSS warning by IDE, will be rmved by validation / security later...
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    // read
    @GetMapping
    public List<Note> getNotes() {
        return notesService.getAllNotes();
    }

    // read one note
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {

        Note note = notesService.getNoteById(id);

        return ResponseEntity.ok(note);
    }

    // delete all
    @DeleteMapping
    public void deleteAll() {
        notesService.deleteAll();
    }

    // delete by id
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        notesService.deleteById(id);

        return ResponseEntity.noContent().build();
    }



}

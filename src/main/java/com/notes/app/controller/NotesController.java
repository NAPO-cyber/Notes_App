package com.notes.app.controller;

import com.notes.app.dto.NoteRequest;
import com.notes.app.dto.NoteResponse;
import com.notes.app.model.Note;
import com.notes.app.service.NotesService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    // create
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {

    // an XSS warning by IDE, will be rmved by validation / security later...
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notesService.createNote(request));
    }

    // read
    @GetMapping
    public Page<NoteResponse> getAllNotes(Pageable pageable) {
        return notesService.getAllNotes(pageable);
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

    // update note by id
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequest request) {

        return ResponseEntity.ok(notesService.updateNote(id, request));
    }

}

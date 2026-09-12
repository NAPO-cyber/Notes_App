package com.notes.app.repository;

import com.notes.app.model.Note;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NotesRepo {

    private Long nextId = 1L;

    private final List<Note> notes = new ArrayList<>();

    public Note save(Note note) {
        note.setId(nextId++);
        notes.add(note);

        return note;
    }

    public List<Note> findAll() {
        return notes;
    }

    public Note findById(Long id) {
        return notes.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void clearAll() {
        notes.clear();
        System.out.println("Notes Cleared.");
    }

    public void clearById(Long id) {
        notes.removeIf(note -> note.getId().equals(id));
        System.out.println("Note Deleted.");
    }


    public Note update(Note note) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(note.getId())) {
                notes.set(i, note);
                return note;
            }
        }
        return null;
    }

}

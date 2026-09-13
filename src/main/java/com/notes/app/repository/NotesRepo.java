package com.notes.app.repository;

import com.notes.app.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotesRepo extends JpaRepository<Note, Long> {
}

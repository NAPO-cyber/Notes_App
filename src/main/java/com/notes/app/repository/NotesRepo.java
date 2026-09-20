package com.notes.app.repository;

import com.notes.app.model.Note;
import com.notes.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface NotesRepo extends JpaRepository<Note, Long> {

    Page<Note> findByUser(User user, Pageable pageable);

    Optional<Note> findByIdAndUser(Long id, User user);

    long deleteByUser(User user);
}

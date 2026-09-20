package com.notes.app.service;

import com.notes.app.dto.NotePatchRequest;
import com.notes.app.dto.NoteRequest;
import com.notes.app.dto.NoteResponse;
import com.notes.app.exception.NoteNotFoundException;
import com.notes.app.mapper.NoteMapper;
import com.notes.app.model.Note;
import com.notes.app.model.User;
import com.notes.app.repository.NotesRepo;
import com.notes.app.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class NotesService {

    private static final Logger log = LoggerFactory.getLogger(NotesService.class);

    private final NoteMapper noteMapper;
    private final NotesRepo notesRepo;
    private final UserRepository userRepository;

    public NotesService(NoteMapper noteMapper, NotesRepo notesRepo, UserRepository userRepository) {
        this.noteMapper = noteMapper;
        this.notesRepo = notesRepo;
        this.userRepository = userRepository;
    }

    public NoteResponse createNote(NoteRequest request) {

        log.info("Creating new note");

        User user = getCurrentUser();
        Note note = new Note();

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setUser(user);

        Note savedNote = notesRepo.save(note);

        log.info("Note created with id: {}", savedNote.getId());

        return noteMapper.toResponse(savedNote);
    }

    public Page<NoteResponse> getAllNotes(Pageable pageable) {
        User user = getCurrentUser();

        Page<NoteResponse> note = notesRepo.findByUser(user, pageable)
                .map(noteMapper::toResponse);

        if (note.isEmpty()) {
            throw new NoteNotFoundException("there are no notes yet!");
        }

        return note;
    }

    public Note getNoteById(Long id) {

        log.info("Fetching note with id: {}", id);

        User user = getCurrentUser();

        return notesRepo.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new NoteNotFoundException("Note not found with id: " + id));
    }

    public void deleteAll() {

        User user = getCurrentUser();

        long deletedCount = notesRepo.deleteByUser(user);

        log.info("Deleted {} notes for user: {}", deletedCount, user.getUsername());
    }

    public void deleteById(Long id) {
        Note note = getNoteById(id);

        notesRepo.delete(note);

        log.info("Deleting note with id: {}", id);
    }

    public NoteResponse updateNote(Long id, NoteRequest request) {

        User user = getCurrentUser();

        Note note = notesRepo.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new NoteNotFoundException("Note not found!"));

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        Note updatedNote = notesRepo.save(note);

        log.info("Updating note with id: {}", id);

        return new NoteResponse(
                updatedNote.getId(),
                updatedNote.getTitle(),
                updatedNote.getContent(),
                updatedNote.getCreatedAt(),
                updatedNote.getUpdatedAt()
        );
    }

    public NoteResponse patchNote(Long id, NotePatchRequest request) {

        User user = getCurrentUser();

        Note note = notesRepo.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new NoteNotFoundException("Note not found!"));

        if (request.getTitle() != null) {
            note.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            note.setContent(request.getContent());
        }

        Note updatedNote = notesRepo.save(note);

        return noteMapper.toResponse(updatedNote);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

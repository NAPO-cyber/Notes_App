package com.notes.app.model;

public class Note {

    private Long id;
    private String title;
    private String content;

    public Note(Long id, String content, String title) {
        this.id = id;
        this.content = content;
        this.title = title;
    }

    public Note() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

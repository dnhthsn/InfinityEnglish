package com.example.infinityenglish.models;

public class Notes {
    private Integer id;
    private String title, content;

    public Notes() {
    }

    public Notes(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Notes(Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

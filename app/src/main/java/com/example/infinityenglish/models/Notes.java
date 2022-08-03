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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

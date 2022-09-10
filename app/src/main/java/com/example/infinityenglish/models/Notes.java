package com.example.infinityenglish.models;

public class Notes {
    private Integer id;
    private String title, content;
    private int uid;

    public Notes() {
    }

    public Notes(String title, String content, int uid) {
        this.title = title;
        this.content = content;
        this.uid = uid;
    }

    public Notes(Integer id, String title, String content, int idUser) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.uid = idUser;
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

    public int getUid() {
        return uid;
    }
}

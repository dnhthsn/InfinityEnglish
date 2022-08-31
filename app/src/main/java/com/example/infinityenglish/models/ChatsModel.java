package com.example.infinityenglish.models;

public class ChatsModel {
    private String message;
    private String sender;

    public ChatsModel(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }
}

package com.example.infinityenglish.models;

public class Phonetics {
    private String text;
    private String audio;

    public Phonetics(String text, String audio) {
        this.text = text;
        this.audio = audio;
    }

    public String getText() {
        return text;
    }

    public String getAudio() {
        return audio;
    }
}

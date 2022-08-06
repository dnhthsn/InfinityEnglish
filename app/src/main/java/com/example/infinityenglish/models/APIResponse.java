package com.example.infinityenglish.models;

import java.util.List;

public class APIResponse {
    private String word;
    private List<Phonetics> phonetics;
    private List<Meanings> meanings;

    public APIResponse(String word, List<Phonetics> phonetics, List<Meanings> meanings) {
        this.word = word;
        this.phonetics = phonetics;
        this.meanings = meanings;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Phonetics> getPhonetics() {
        return phonetics;
    }

    public List<Meanings> getMeanings() {
        return meanings;
    }
}

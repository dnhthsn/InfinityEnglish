package com.example.infinityenglish.models;

import java.util.List;

public class ListWords {
    private List<String> all;

    public ListWords(List<String> all) {
        this.all = all;
    }

    public List<String> getWords() {
        return all;
    }

    public void setWords(List<String> all) {
        this.all = all;
    }
}

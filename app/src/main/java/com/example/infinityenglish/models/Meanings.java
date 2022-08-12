package com.example.infinityenglish.models;

import java.util.List;

public class Meanings {
    private String partOfSpeech;
    private List<Definitions> definitions;

    public Meanings(String partOfSpeech, List<Definitions> definitions) {
        this.partOfSpeech = partOfSpeech;
        this.definitions = definitions;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<Definitions> getDefinitions() {
        return definitions;
    }
}

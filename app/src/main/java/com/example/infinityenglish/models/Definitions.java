package com.example.infinityenglish.models;

import java.util.List;

public class Definitions {
    private String definition;
    private String example;
    private List<String> synonyms;
    private List<String> antonyms;

    public Definitions(String definition, String example, List<String> synonyms, List<String> antonyms) {
        this.definition = definition;
        this.example = example;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }
}

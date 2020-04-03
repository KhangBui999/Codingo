package com.example.codingo.Model;

public class Flashcard {
    private String id;
    private String content;
    private String answer;

    public Flashcard() {
    }

    public Flashcard(String id, String content, String answer) {
        this.id = id;
        this.content = content;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

package com.example.codingo.Entities;

import java.util.ArrayList;

public class Question {
    private String id;
    private String question;
    private ArrayList<String> answers; //database attribute will be answer1, answer2, etc...
    private int correctPosition;

    public Question() {
    }

    public Question(String id, String question, ArrayList<String> answers, int correctPosition) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correctPosition = correctPosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getCorrectPosition() {
        return correctPosition;
    }

    public void setCorrectPosition(int correctPosition) {
        this.correctPosition = correctPosition;
    }
}

package com.example.codingo.Model;

import java.util.ArrayList;

public class MultipleChoice extends Questions {
    private ArrayList<String> responses;
    private String answer;

    public MultipleChoice(){
    }

    public MultipleChoice(String id, String question, ArrayList<String> responses, String answer) {
        super(id, question);
        this.responses = responses;
        this.answer = answer;
    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<String> responses) {
        this.responses = responses;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

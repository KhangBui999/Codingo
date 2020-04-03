package com.example.codingo.Model;

import java.util.ArrayList;

public class FillTheBlanks extends Questions {
    private ArrayList<String> blanks;
    private ArrayList<String> answer;

    public FillTheBlanks() {
    }

    public FillTheBlanks(String id, String question, ArrayList<String> blanks, ArrayList<String> answer) {
        super(id, question);
        this.blanks = blanks;
        this.answer = answer;
    }

    public ArrayList<String> getBlanks() {
        return blanks;
    }

    public void setBlanks(ArrayList<String> blanks) {
        this.blanks = blanks;
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }
}

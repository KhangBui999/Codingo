/*
 * INFS3634 Group Assignment 2020 T1 - Team 31
 *
 * This is an Android mobile application that showcases the use of functional Android building blocks
 * and the implementation of other features such as Google Firebase and API calls. Submitted as part of
 * a group assignment for the course, INFS3634.
 *
 * Authors:
 * Shara Bakal, Khang Bui, Laurence Truong & Brian Vu
 *
 */

package com.example.codingo.Entities;

import java.util.ArrayList;

/**
 * Model class for the quiz questions in QuizActivity
 */
public class Question {
    private String id;
    private String question;
    private ArrayList<String> answers; //database attribute will be answer1, answer2, etc...
    private int correctPosition;

    /**
     * Constructor class for the Question class
     * @param id is the document ID in the Firebase server
     * @param question is the question prompt e.g. What is a String?
     * @param answers is the list of multiple-choice answers (only 4 answers are used)
     * @param correctPosition is the correct position of the answer in the above list
     */
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

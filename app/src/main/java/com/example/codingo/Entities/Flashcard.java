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

/**
 * Model class for the Flashcard
 * Used to hold flashcards from the Firebase server in the FlashcardFragment.
 */
public class Flashcard {
    private String id;
    private String content;
    private String answer;

    /**
     * Flashcard object constructor
     * @param id is the Document ID from the Firebase server
     * @param content is the content/question (front of card)
     * @param answer is the answer (back of card)
     */
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

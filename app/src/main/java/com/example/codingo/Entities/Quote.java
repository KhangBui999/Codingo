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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Quote model class - serves as an entity class for the Programming Quotes API
 * Used as a data model in the HomeFragment.
 */
public class Quote {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("en")
    @Expose
    private String en;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("id")
    @Expose
    private String id;


    public Quote(String _id, String en, String author, String id) {
        this._id = id;
        this.en = en;
        this.author = author;
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}


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

package com.example.codingo;

import com.example.codingo.Entities.Quote;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Service class for the Programming Quotes API
 */
public interface QuoteService {

    @GET("quotes/random")
    Call<Quote> getQuote();

}

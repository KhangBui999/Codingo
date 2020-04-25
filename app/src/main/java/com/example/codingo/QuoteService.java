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

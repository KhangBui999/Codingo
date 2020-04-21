package com.example.codingo;

import com.example.codingo.Model.Quote;

import retrofit2.Call;
import retrofit2.http.GET;

//quotes/random
public interface QuoteService {

    @GET("quotes/random")
    Call<Quote> getQuote();

}

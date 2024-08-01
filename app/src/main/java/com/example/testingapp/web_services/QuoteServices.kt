package com.example.testingapp.web_services

import com.example.testingapp.model.QuoteList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesServices {

    // BASE_URL+/quotes?page=1
    @GET("quotes")
    suspend fun getQuotes(@Query("page")page:Int): Response<QuoteList>
}

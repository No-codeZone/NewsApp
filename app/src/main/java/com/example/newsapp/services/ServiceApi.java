package com.example.newsapp.services;

import com.example.newsapp.model.NewsRequestModel;
import com.example.newsapp.model.TopHeadlinesResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {
    @GET("everything")
    Call<NewsRequestModel> getEverything(
            @Query("q") String query,
//            @Query("from") String fromDate,
//            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );
    @GET("top-headlines")
    Call<TopHeadlinesResponseModel> getTopHeadlines(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );
}
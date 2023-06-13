package com.example.newsapp.apis;

import com.example.newsapp.models.newsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsAPI {
    @GET
    public Call<newsModel> getAllArticles(@Url String url);

    @GET
    public Call<newsModel> getArticlesByCategory(@Url String url);
}

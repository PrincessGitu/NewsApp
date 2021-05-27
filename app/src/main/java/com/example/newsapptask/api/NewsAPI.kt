package com.example.newsapptask.api

import com.example.newsapptask.model.NewsResponse
import com.example.newsapptask.utils.Constant.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Gitanjali Ghangale on 5/22/2021.
 */
interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getTechnologyNews(
        @Query("country") countryCode: String , @Query("category")
        category: String ,  @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY): Response<NewsResponse>
}
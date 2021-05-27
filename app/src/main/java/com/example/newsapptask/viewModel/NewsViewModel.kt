package com.example.newsapptask.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptask.model.Article
import com.example.newsapptask.model.NewsResponse
import com.example.newsapptask.repository.NewsRepository
import com.example.newsapptask.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by Gitanjali Ghangale on 5/22/2021.
 */
class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val topTechnologyNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var technologyNewsPage = 1


    val topHealthNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var healthNewsPage = 1


    fun getTechnologyNews(countryCode: String, category: String) = viewModelScope.launch {
        topTechnologyNews.postValue(Resource.Loading())
        val response = newsRepository.getTechnologyNews(countryCode, category, technologyNewsPage)
        topTechnologyNews.postValue(handleTechnologyNewsResponse(response))

    }

    private fun handleTechnologyNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

//                for(element in resultResponse.articles){
//                    Log.e("check","="+isNewsAlreadyExit(element).toString())
//                }

                addFavoriteList(resultResponse.articles)
                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }

    fun getHealthNews(countryCode: String, category: String) = viewModelScope.launch {
        topHealthNews.postValue(Resource.Loading())
        val response = newsRepository.getTechnologyNews(countryCode, category, healthNewsPage)
        topHealthNews.postValue(handleHealthNewsResponse(response))

    }

    private fun handleHealthNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }


    fun getAllNews() = newsRepository.getAllNews()
    fun getAllFavoriteNews() = newsRepository.getFavoriteNews(true)

    fun addFavorite(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    private fun addFavoriteList(article: List<Article>) = viewModelScope.launch {
        newsRepository.upsertList(article)
    }
    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.delete(article)
    }
    fun deleteFavoriteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteFavoriteArticles(false,article)
    }

    private fun isNewsAlreadyExit(article: Article)=viewModelScope.launch {
       newsRepository.isNewIsExit(article)
       // newsRepository.isCount(article)
    }
}
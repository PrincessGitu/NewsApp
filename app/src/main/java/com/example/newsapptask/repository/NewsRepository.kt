package com.example.newsapptask.repository

import com.example.newsapptask.api.RetrofitInstance
import com.example.newsapptask.database.ArticleDatabase
import com.example.newsapptask.model.Article

/**
 * Created by Gitanjali Ghangale on 5/22/2021.
 */
class NewsRepository(private val db:ArticleDatabase) {

    suspend fun getTechnologyNews(countryCode: String, category: String,pageNumber: Int) =
        RetrofitInstance.api.getTechnologyNews(countryCode,category, pageNumber)



    suspend fun upsert(article: Article)=db.getArticleDao().upsert(article)
    suspend fun upsertList(articles: List<Article>)=db.getArticleDao().upsertList(articles)
    suspend fun delete(article: Article)=db.getArticleDao().deleteArticle(article)
    suspend fun deleteFavoriteArticles(isFav:Boolean,article: Article)=db.getArticleDao().deleteFavoriteArticles(isFav,article.title)
    fun getAllNews()=db.getArticleDao().getAllArticles()
    fun getFavoriteNews(isFav:Boolean)=db.getArticleDao().getFavoriteArticles(isFav)

    suspend  fun isNewIsExit(article: Article)=db.getArticleDao().isNewsIsExist(article.title)
     suspend  fun isCount(article: Article)=db.getArticleDao().count(article.title)
}
package com.example.newsapptask.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapptask.model.Article

/**
 * Created by Gitanjali Ghangale on 5/22/2021.
 */
@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertList(article: List<Article>)

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM ARTICLES WHERE favorite=:isFavorite")
    fun getFavoriteArticles(isFavorite:Boolean):LiveData<List<Article>>

    @Query("UPDATE ARTICLES SET favorite=:isFavorite WHERE title=:title")
    suspend fun deleteFavoriteArticles(isFavorite:Boolean,title:String)

    @Query("SELECT EXISTS(SELECT * FROM articles WHERE title=:title)")
    suspend fun isNewsIsExist(title:String) : Int

    @Query("SELECT * FROM articles WHERE title=:title")
    suspend fun isNewsAlExist(title:String):Article

    @Query("SELECT COUNT() FROM articles WHERE title=:title")
    suspend fun count(title:String): Int
}
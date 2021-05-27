package com.example.newsapptask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "articles")
data class Article(
        var id: Int? = null,
        val author: String?,
        val content: String?,
        val description: String?,
        val publishedAt: String?,
        val source: Source?,
        @NotNull
        @PrimaryKey(autoGenerate = false)
        val title: String,
        val url: String?,
        val urlToImage: String?,
        var favorite: Boolean = false
) : Serializable
package com.example.newsapptask.providerFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapptask.repository.NewsRepository
import com.example.newsapptask.viewModel.NewsViewModel

/**
 * Created by Gitanjali Ghangale on 5/22/2021.
 */
@Suppress("UNCHECKED_CAST")
class NewsProviderFactory(private val newsRepository: NewsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return NewsViewModel(newsRepository) as T
    }
}
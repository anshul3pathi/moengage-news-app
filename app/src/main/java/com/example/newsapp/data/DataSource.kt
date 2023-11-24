package com.example.newsapp.data

import com.example.newsapp.data.remote.dto.ArticleResponse

// The various data sources in the app, for example - remote api or Room DB will implement
// this abstract data source
interface DataSource {
    suspend fun getArticles(): Result<List<ArticleResponse>>
}
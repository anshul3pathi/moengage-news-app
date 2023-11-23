package com.example.newsapp.data

import com.example.newsapp.data.remote.dto.ArticleResponse

interface DataSource {
    suspend fun getArticles(): Result<List<ArticleResponse>>
}
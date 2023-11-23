package com.example.newsapp.data.remote.dto

data class NewsResponse(
    val articles: List<ArticleResponse>?,
    val status: String?
)
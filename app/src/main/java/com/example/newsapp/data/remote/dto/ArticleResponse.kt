package com.example.newsapp.data.remote.dto

data class ArticleResponse(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlOfImage: String?,
    val source: ArticleSourceResponse?
)

data class ArticleSourceResponse(
    val id: String?,
    val name: String?
)
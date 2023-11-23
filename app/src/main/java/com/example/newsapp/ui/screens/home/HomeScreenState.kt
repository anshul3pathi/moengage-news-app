package com.example.newsapp.ui.screens.home

import com.example.newsapp.data.remote.dto.ArticleResponse
import com.example.newsapp.utils.convertIsoDateToTimeInMillis
import java.lang.Exception

sealed class HomeScreenState {
    object Loading : HomeScreenState()

    data class Success(val articles: List<ArticleUi>) : HomeScreenState()

    data class Error(val errorMessage: String, val exception: Exception?) : HomeScreenState()
}

data class ArticleUi(
    val id: String,
    val imageUrl: String?,
    val articleUrl: String?,
    val author: String,
    val title: String,
    val description: String,
    val publishTimeInMillis: Long,
)

fun ArticleResponse.toArticleUi(): ArticleUi? {
    return ArticleUi(
        id = source?.id ?: return null, // id is required
        author = author ?: "",
        imageUrl = urlOfImage,
        articleUrl = url,
        title = title ?: "",
        description = description ?: "",
        publishTimeInMillis = publishedAt?.convertIsoDateToTimeInMillis() ?: 0L
    )
}
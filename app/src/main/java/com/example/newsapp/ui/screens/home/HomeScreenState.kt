package com.example.newsapp.ui.screens.home

import com.example.newsapp.data.remote.dto.ArticleResponse
import com.example.newsapp.utils.convertIsoDateToTimeInMillis
import com.example.newsapp.utils.convertToDateInStandardFormat
import com.example.newsapp.utils.convertToStandardTime

sealed class HomeScreenState {
    object Loading : HomeScreenState()

    data class Success(val articles: List<ArticleUi>) : HomeScreenState()

    data class Error(val errorMessage: String, val exception: Throwable? = null) : HomeScreenState()
}

data class ArticleUi(
    val id: String,
    val imageUrl: String?,
    val articleUrl: String?,
    val author: String,
    val title: String,
    val description: String,
    val publishTimeInMillis: Long,
    val publishDate: String? = null,
    val publishTime: String? = null
)

enum class ArticlesSortOrder {
    NEW_TO_OLD,
    OLD_TO_NEW
}

fun ArticleResponse.toArticleUi(): ArticleUi? {
    val publishTimeInMillis = publishedAt?.convertIsoDateToTimeInMillis() ?: 0L
    return ArticleUi(
        id = source?.id ?: return null, // id is required
        author = author ?: "",
        imageUrl = urlOfImage,
        articleUrl = url,
        title = title ?: "",
        description = description ?: "",
        publishTimeInMillis = publishTimeInMillis,
        publishDate = publishTimeInMillis.convertToDateInStandardFormat(),
        publishTime = publishTimeInMillis.convertToStandardTime()
    )
}
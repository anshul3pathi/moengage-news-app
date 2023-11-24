package com.example.newsapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.DataSource
import com.example.newsapp.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val remote: DataSource = RemoteDataSource()
) : ViewModel() {

    private var articlesSortOrder = ArticlesSortOrder.NEW_TO_OLD

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        getArticles()
    }

    fun onRetryFetchingArticles() {
        _state.update { HomeScreenState.Loading }
        getArticles()
    }

    fun onChangeSortOrderOfArticlesTo(articlesSortOrder: ArticlesSortOrder) {
        if (state.value !is HomeScreenState.Success) {
            // if we are not in Success state, articles are not present and changing sorting order
            // is meaningless
            return
        }

        this.articlesSortOrder = articlesSortOrder
        val articles = (state.value as HomeScreenState.Success).articles
        val sortedArticles = getSortedArticles(articles)

        _state.update {
            HomeScreenState.Success(sortedArticles)
        }
    }

    private fun getArticles() {
        viewModelScope.launch {
            remote.getArticles()
                .onSuccess { articlesResponses ->
                    _state.update {
                        val articles = articlesResponses.mapNotNull { articleResponse ->
                            articleResponse.toArticleUi()
                        }
                        val sortedArticles = getSortedArticles(articles = articles)

                        HomeScreenState.Success(articles = sortedArticles)
                    }
                }
                .onFailure { error ->
                    _state.update {
                        HomeScreenState.Error(
                            errorMessage = error.localizedMessage ?: "",
                            exception = error
                        )
                    }
                }
        }
    }

    private fun getSortedArticles(articles: List<ArticleUi>): List<ArticleUi> {
        return when (articlesSortOrder) {
            ArticlesSortOrder.NEW_TO_OLD -> articles.sortedByDescending { it.publishTimeInMillis }
            ArticlesSortOrder.OLD_TO_NEW -> articles.sortedBy { it.publishTimeInMillis }
        }
    }
}
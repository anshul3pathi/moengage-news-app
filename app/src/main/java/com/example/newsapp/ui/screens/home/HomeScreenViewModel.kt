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

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        getArticles()
    }

    fun onRetryFetchingArticles() {
        _state.update { HomeScreenState.Loading }
        getArticles()
    }

    private fun getArticles() {
        println("articleUi - fetching")
        viewModelScope.launch {
            remote.getArticles()
                .onSuccess { articlesResponses ->
                    _state.update {
                        HomeScreenState.Success(
                            articles = articlesResponses.mapNotNull { articleResponse ->
                                val x = articleResponse.toArticleUi()
                                println("articleUi -> $x")
                                x
                            }
                        )
                    }
                }
                .onFailure { exception -> println("articleUi $exception")  }
        }
    }
}
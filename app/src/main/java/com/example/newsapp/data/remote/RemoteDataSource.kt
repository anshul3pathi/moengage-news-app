package com.example.newsapp.data.remote

import com.example.newsapp.data.DataSource
import com.example.newsapp.data.remote.api.ApiService
import com.example.newsapp.data.remote.api.HttpApiService
import com.example.newsapp.data.remote.api.NewsResponseJsonParser
import com.example.newsapp.data.remote.api.NewsResponseParser
import com.example.newsapp.data.remote.dto.ArticleResponse
import java.lang.IllegalStateException

class RemoteDataSource(
    private val apiService: ApiService = HttpApiService(),
    private val parser: NewsResponseParser = NewsResponseJsonParser()
) : DataSource {
    override suspend fun getArticles(): Result<List<ArticleResponse>> {
        return try {
            val response = apiService.fetchNewsArticles()
            val newsResponse = parser.parse(response)
            newsResponse?.articles?.let {
                Result.success(it)
            } ?: Result.failure(IllegalStateException("Either NewsResponse or List<ArticleResponse> was null."))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
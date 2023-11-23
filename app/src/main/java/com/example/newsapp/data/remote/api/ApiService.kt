package com.example.newsapp.data.remote.api

typealias Response = String

interface ApiService {
    suspend fun fetchNewsArticles(): Response
}
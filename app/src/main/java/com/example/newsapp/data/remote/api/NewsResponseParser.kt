package com.example.newsapp.data.remote.api

import com.example.newsapp.data.remote.dto.NewsResponse

interface NewsResponseParser {
    fun parse(response: Response): NewsResponse?
}
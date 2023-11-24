package com.example.newsapp.data.remote.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

private const val NEWS_API_ENDPOINT = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"
private const val UTF8 = "UTF-8"
private const val END_LINE_CHARACTER = "\n"

/**
 * Custom implementation of the ApiService using HttpURLConnection
 */
class HttpApiService : ApiService {

    override suspend fun fetchNewsArticles(): Response = withContext(Dispatchers.IO) {
        val url = URL(NEWS_API_ENDPOINT)
        val urlConnection = url.openConnection() as HttpURLConnection

        try {
            val inputStream = urlConnection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream, UTF8))
            val responseStringBuilder = StringBuilder()

            var line: String? = reader.readLine()
            while (line != null) {
                responseStringBuilder.append(line).append(END_LINE_CHARACTER)
                line = reader.readLine()
            }

            return@withContext responseStringBuilder.toString()
        } finally {
            urlConnection.disconnect()
        }
    }
}
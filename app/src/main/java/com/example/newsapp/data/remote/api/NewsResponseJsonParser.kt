package com.example.newsapp.data.remote.api

import com.example.newsapp.data.remote.dto.ArticleResponse
import com.example.newsapp.data.remote.dto.ArticleSourceResponse
import com.example.newsapp.data.remote.dto.NewsResponse
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class NewsResponseJsonParser : NewsResponseParser {
    override fun parse(response: Response): NewsResponse? {
        return try {
            val responseJsonObject = JSONObject(response)
            val status = responseJsonObject.getString("status")
            val articlesResponseArray = responseJsonObject.optJSONArray("articles")

            return NewsResponse(
                status = status,
                articles = parseArticles(articlesResponseArray)
            )
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    private fun parseArticles(articlesArray: JSONArray?): List<ArticleResponse>? {
        return articlesArray?.let {
            (0 until it.length()).map { i ->
                val articleObject = it.optJSONObject(i)
                ArticleResponse(
                    author = articleObject?.optString("author"),
                    content = articleObject?.optString("content"),
                    description = articleObject?.optString("description"),
                    publishedAt = articleObject?.optString("publishedAt"),
                    title = articleObject?.optString("title"),
                    url = articleObject?.optString("url"),
                    urlOfImage = articleObject?.optString("urlToImage"),
                    source = with(articleObject) {
                        val id = getJSONObject("source").getString("id") ?: return null
                        val name = getJSONObject("source").getString("name") ?: null

                        ArticleSourceResponse(
                            id = id,
                            name = name
                        )
                    }
                )
            }
        }
    }
}
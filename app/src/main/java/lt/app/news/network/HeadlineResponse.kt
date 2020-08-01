package lt.app.news.network

import lt.app.news.data.Article

data class HeadlineResponse(
    val status: String?,
    val totalResults: Long,
    val articles: List<Article>?
)
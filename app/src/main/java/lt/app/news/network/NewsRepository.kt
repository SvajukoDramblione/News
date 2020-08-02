package lt.app.news.network

import io.reactivex.Single

data class NewsRepository(private val api: Api) {
    fun getArticles(country: String): Single<HeadlineResponse> {
        return api.getTopHeadlines(country)
    }
}

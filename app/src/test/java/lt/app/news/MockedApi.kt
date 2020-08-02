package lt.app.news

import io.reactivex.Single
import io.reactivex.Single.error
import lt.app.news.data.Article
import lt.app.news.network.Api
import lt.app.news.network.HeadlineResponse
import java.lang.Exception

class MockedApi: Api {
    override fun getTopHeadlines(country: String): Single<HeadlineResponse> {
        if (country.isNotEmpty()) {
            return Single.fromCallable {
                val articlesList: MutableList<Article> = ArrayList()
                val article = Article()
                articlesList.add(article)
                val headlineResponse = HeadlineResponse(
                    "ok",
                    1,
                    articlesList
                )
                headlineResponse
            }
        } else {
            return error(Exception("Missing parameter"))
        }
    }
}
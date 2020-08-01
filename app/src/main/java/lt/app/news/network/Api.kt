package lt.app.news.network

import io.reactivex.Single
import lt.havefun.tesonetfunparty.Config
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("top-headlines?apiKey=" + Config.NEWS_API_KEY)
    fun getTopHeadlines(
        @Query("country") country: String
    ): Single<HeadlineResponse>
}
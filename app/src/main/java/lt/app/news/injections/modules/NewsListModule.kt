package lt.app.news.injections.modules

import dagger.Module
import dagger.Provides
import lt.app.news.network.Api
import lt.app.news.network.NewsRepository
import lt.app.news.ui.newslist.NewsListViewModel
import javax.inject.Singleton

@Module
class NewsListModule {

    @Provides
    @Singleton
    fun providesMainViewModelFactory(
        mainRepository: NewsRepository
    ): NewsListViewModel.Factory {
        return NewsListViewModel.Factory(
            mainRepository
        )
    }

    @Provides
    @Singleton
    fun providesMainRepository(api: Api): NewsRepository {
        return NewsRepository(api)
    }
}
package lt.app.news.injections.modules

import dagger.Module
import dagger.Provides
import lt.app.news.ui.newsdetails.NewsDetailsViewModel
import javax.inject.Singleton

@Module
class NewsDetailsModule {

    @Provides
    @Singleton
    fun providesDetailsViewModelFactory(
    ): NewsDetailsViewModel.Factory {
        return NewsDetailsViewModel.Factory()
    }
}
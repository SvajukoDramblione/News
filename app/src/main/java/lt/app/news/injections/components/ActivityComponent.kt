package lt.app.news.injections

import dagger.Component
import lt.app.news.MainActivity
import lt.app.news.injections.modules.ActivityModule
import lt.app.news.injections.modules.ApiModule
import lt.app.news.injections.modules.AppModule
import lt.app.news.injections.modules.NewsListModule
import lt.app.news.ui.newslist.NewsListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ActivityModule::class,
    ApiModule::class,
    NewsListModule::class
])

interface ActivityComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: NewsListFragment)
}
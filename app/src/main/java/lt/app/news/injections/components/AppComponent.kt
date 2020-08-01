package lt.app.news.injections.components

import dagger.Component
import lt.app.news.App
import lt.app.news.injections.modules.ApiModule
import lt.app.news.injections.modules.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ApiModule::class
])
interface AppComponent {
    fun inject(application: App)
}

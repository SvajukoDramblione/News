package lt.app.news.injections.modules

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @Singleton
    internal fun provideActivityContext(): Context = activity

    @Provides
    @Singleton
    internal fun provideActivity(): Activity = activity
}
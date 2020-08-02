package lt.app.news

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import lt.app.news.data.events.ShowNewsDetailsEvent
import lt.app.news.injections.components.DaggerActivityComponent
import lt.app.news.injections.components.ActivityComponent
import lt.app.news.injections.modules.AppModule
import lt.app.news.ui.newslist.NewsListFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var eventBus: EventBus

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        prepareActivityComponent()
        activityComponent.inject(this)

        if (savedInstanceState == null) {
            goToNewsFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        eventBus.register(this)
    }

    override fun onStop() {
        super.onStop()
        eventBus.unregister(this)
    }

    @Subscribe
    fun onEvent(event: ShowNewsDetailsEvent) {
        Log.d("ShowNewsDetailsEvent", event.article.title.toString())
    }

    private fun goToNewsFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            .replace(R.id.container, NewsListFragment.newInstance())
            .commitNow()
    }

    private fun prepareActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
            .appModule(
                AppModule(
                    App.getApplication(this)
                )
            )
            .build()
    }
}
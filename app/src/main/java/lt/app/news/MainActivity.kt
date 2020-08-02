package lt.app.news

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import lt.app.news.data.Article
import lt.app.news.data.ArticleDetails
import lt.app.news.data.events.ShowNewsDetailsEvent
import lt.app.news.injections.components.DaggerActivityComponent
import lt.app.news.injections.components.ActivityComponent
import lt.app.news.injections.modules.AppModule
import lt.app.news.ui.newsdetails.NewsDetailsFragment
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        } else {
            super.onBackPressed()
        }
    }

    @Subscribe
    fun onEvent(event: ShowNewsDetailsEvent) {
        Log.d("ShowNewsDetailsEvent", event.article.title.toString())
        goToDetailsFragment(event.article)
    }

    private fun goToNewsFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            .add(R.id.container, NewsListFragment.newInstance())
            .commitNow()
    }

    private fun goToDetailsFragment(article: Article) {
        val articleDetails = ArticleDetails(
            article.author,
            article.title,
            article.description,
            article.url,
            article.urlToImage,
            article.publishedAt
        )
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            .add(R.id.container, NewsDetailsFragment.newInstance(articleDetails))
            .addToBackStack(null)
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
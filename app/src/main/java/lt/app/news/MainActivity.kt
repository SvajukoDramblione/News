package lt.app.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import lt.app.news.injections.ActivityComponent
import lt.app.news.injections.DaggerActivityComponent
import lt.app.news.injections.modules.ActivityModule
import lt.app.news.injections.modules.ApiModule
import lt.app.news.injections.modules.AppModule
import org.greenrobot.eventbus.EventBus
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

        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null) {
            // go to initial fragment
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
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
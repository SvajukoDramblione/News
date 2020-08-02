package lt.app.news.ui.newslist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import lt.app.news.MainActivity
import lt.app.news.R
import lt.app.news.data.Article
import lt.app.news.data.events.ShowNewsDetailsEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NewsListFragment: Fragment() {

    @Inject
    lateinit var eventBus: EventBus

    @Inject
    lateinit var viewModelFactory: NewsListViewModel.Factory

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingViewsLayout: ConstraintLayout
    private lateinit var viewModel: NewsListViewModel

    private var longAnimDuration: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_ll)
        recyclerView = view.findViewById(R.id.news_rv)
        loadingViewsLayout = view.findViewById(R.id.loading_cl)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        longAnimDuration = resources.getInteger(android.R.integer.config_longAnimTime)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(NewsListViewModel::class.java)

        addObservers()
        getArticles()

        addSwipeToRefreshListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).activityComponent.inject(this)
    }

    private fun getArticles() {
        viewModel.getArticles(COUNTRY_CODE)
    }

    private fun addSwipeToRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener {
            getArticles()
        }
    }

    private fun addObservers() {
        addDataObserver()
        addErrorObserver()
    }

    private fun addDataObserver() {
        viewModel.data.observe(viewLifecycleOwner, Observer { articles ->
            showCrossfadeAnimation()
            with(recyclerView) {
                adapter?.let {
                    (it as NewsRecyclerViewAdapter).updateValues(articles)
                    swipeRefreshLayout.isRefreshing = false
                } ?: run {
                    adapter = NewsRecyclerViewAdapter(articles) {
                        eventBus.post(ShowNewsDetailsEvent(it))
                    }
                }
            }
        })
    }

    private fun addErrorObserver() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            val alertBuilder = AlertDialog.Builder(requireContext())
            alertBuilder.setTitle(R.string.error)
            alertBuilder.setCancelable(false)
            alertBuilder.setPositiveButton(R.string.refresh) { _, _ ->
                getArticles()
                swipeRefreshLayout.isRefreshing = false
            }
            alertBuilder.show()
        })
    }

    private fun showCrossfadeAnimation() {
        swipeRefreshLayout.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(longAnimDuration.toLong())
                .setListener(null)
        }
        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        loadingViewsLayout.animate()
            .alpha(0f)
            .setDuration(longAnimDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    loadingViewsLayout.visibility = View.GONE
                }
            })
    }

    companion object {
        private const val COUNTRY_CODE = "lt"
        fun newInstance() = NewsListFragment()
    }
}
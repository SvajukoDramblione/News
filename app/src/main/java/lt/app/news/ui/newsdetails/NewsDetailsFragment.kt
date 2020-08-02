package lt.app.news.ui.newsdetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import lt.app.news.MainActivity
import lt.app.news.R
import lt.app.news.data.ArticleDetails
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NewsDetailsFragment: Fragment() {

    @Inject
    lateinit var eventBus: EventBus

    @Inject
    lateinit var viewModelFactory: NewsDetailsViewModel.Factory

    private lateinit var viewModel: NewsDetailsViewModel
    private lateinit var imageView: ImageView
    private lateinit var titleTV: TextView
    private lateinit var descriptionTV: TextView
    private lateinit var authorTV: TextView
    private lateinit var dateTV: TextView
    private lateinit var fullArticleBtn: Button

    private var details: ArticleDetails? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_details, container, false)
        imageView = view.findViewById(R.id.image_iv)
        titleTV = view.findViewById(R.id.title_tv)
        descriptionTV = view.findViewById(R.id.description_tv)
        authorTV = view.findViewById(R.id.author_tv)
        dateTV = view.findViewById(R.id.date_tv)
        fullArticleBtn = view.findViewById(R.id.full_article_btn)

        details = arguments?.getParcelable(NEWS_DETAILS)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(NewsDetailsViewModel::class.java)
        prepareViews()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).activityComponent.inject(this)
    }

    private fun prepareViews() {
        details?.let {
            it.urlToImage?.let { urlToImage ->
                Glide.with(requireContext()).load(urlToImage).into(imageView)
            }
            titleTV.text = it.title
            descriptionTV.text = it.description
            authorTV.text = it.author
            it.publishedAt?.let { date ->
                dateTV.text = viewModel.getFormattedDate(date)
            }
        }

        fullArticleBtn.setOnClickListener {
            details?.url?.let {
                openUrlInBrowser(it)
            }
        }
    }

    private fun openUrlInBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    companion object {
        private const val NEWS_DETAILS = "news_details"
        fun newInstance(
            details: ArticleDetails
        ): NewsDetailsFragment {
            val bundle = Bundle().apply {
                putParcelable(NEWS_DETAILS, details)
            }
            return NewsDetailsFragment().apply {
                arguments = bundle
            }
        }
    }
}
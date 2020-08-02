package lt.app.news.newslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import lt.app.news.MockedApi
import lt.app.news.RxSchedulerRule
import lt.app.news.data.Article
import lt.app.news.network.NewsRepository
import lt.app.news.ui.newslist.NewsListViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NewsListViewModelUnitTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var dataObserver: Observer<List<Article>>

    @Mock
    lateinit var errorObserver: Observer<String>

    private lateinit var newsListViewModel: NewsListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val mockedApi = MockedApi()
        newsListViewModel = NewsListViewModel(NewsRepository(mockedApi))
    }

    @Test
    fun listNotEmpty() {
        newsListViewModel.data.observeForever(dataObserver)
        newsListViewModel.getArticles("lt")

        Mockito.verify(dataObserver).onChanged(Mockito.argThat {
            it.isNotEmpty()
        })
    }

    @Test
    fun dataNotNull() {
        newsListViewModel.data.observeForever(dataObserver)
        newsListViewModel.getArticles("lt")

        Mockito.verify(dataObserver, Mockito.never()).onChanged(null)
    }

    @Test
    fun errorNotReceived() {
        newsListViewModel.error.observeForever(errorObserver)
        newsListViewModel.getArticles("lt")

        Mockito.verify(errorObserver, Mockito.never()).onChanged("error")
    }

    @Test
    fun errorNotReceived2() {
        newsListViewModel.error.observeForever(errorObserver)
        newsListViewModel.getArticles("lt")

        Mockito.verifyZeroInteractions(errorObserver)
    }

    @Test
    fun dataNotReceived() {
        newsListViewModel.data.observeForever(dataObserver)
        newsListViewModel.getArticles("")

        Mockito.verifyZeroInteractions(dataObserver)
    }

    @Test
    fun dataNotReceived2() {
        newsListViewModel.data.observeForever(dataObserver)
        newsListViewModel.getArticles("")

        Mockito.verify(dataObserver, Mockito.never()).onChanged(Mockito.argThat {
            it.isNotEmpty()
        })
    }

    @Test
    fun errorReceived() {
        newsListViewModel.error.observeForever(errorObserver)
        newsListViewModel.getArticles("")

        Mockito.verify(errorObserver).onChanged("error")
    }
}
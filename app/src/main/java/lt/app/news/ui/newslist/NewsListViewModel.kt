package lt.app.news.ui.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lt.app.news.data.Article
import lt.app.news.network.NewsRepository

class NewsListViewModel(
    private val mainRepository: NewsRepository
): ViewModel() {
    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val mainRepository: NewsRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsListViewModel(
                mainRepository
            ) as T
        }
    }

    private val _data = MutableLiveData<List<Article>>()
    val data: LiveData<List<Article>> = _data

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var subscription = CompositeDisposable()

    fun getArticles(country: String) {
        mainRepository.getArticles(country)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let {
                    _data.postValue(it.articles)
                } ?: _error.postValue("error")
            }, { _ ->
                _error.postValue("error")
            })
            .also {
                subscription.add(it)
            }
    }

    override fun onCleared() {
        subscription.dispose()
        super.onCleared()
    }
}
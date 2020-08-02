package lt.app.news.ui.newsdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lt.app.news.Utils
import java.util.Date

class NewsDetailsViewModel : ViewModel() {
    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsDetailsViewModel() as T
        }
    }

    fun getFormattedDate(date: Date): String = Utils.getFormattedDate(date)
}
package lt.app.news

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

object Utils {
    fun getFormattedDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MMM-dd HH:mm", Locale.getDefault())
        return formatter.format(date)
    }
}
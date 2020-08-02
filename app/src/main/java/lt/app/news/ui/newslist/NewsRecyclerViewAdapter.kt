package lt.app.news.ui.newslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lt.app.news.R
import lt.app.news.data.Article
import java.text.SimpleDateFormat
import java.util.*

class NewsRecyclerViewAdapter(
    private var values: List<Article>,
    private val onClickListener: (Article) -> Unit
) : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        with(item) {
            holder.descriptionView.text = title
            var publishDateStr = ""
            publishedAt?.let {
                publishDateStr = getFormattedDate(it)
            }
            holder.dateView.text = publishDateStr
            holder.itemView.context

            Glide
                .with(holder.itemView.context)
                .load(urlToImage)
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int = values.size

    fun updateValues(updatedValues: List<Article>) {
        values = updatedValues
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_iv)
        val descriptionView: TextView = view.findViewById(R.id.description_tv)
        val dateView: TextView = view.findViewById(R.id.date_tv)

        init {
            view.setOnClickListener {
                onClickListener.invoke(values[adapterPosition])
            }
        }
    }

    private fun getFormattedDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MMM-dd HH:mm", Locale.ENGLISH)
        return formatter.format(date)
    }
}
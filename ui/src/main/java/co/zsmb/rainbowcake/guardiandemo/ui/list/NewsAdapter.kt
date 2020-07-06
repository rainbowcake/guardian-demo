package co.zsmb.rainbowcake.guardiandemo.ui.list

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.guardiandemo.ui.R
import co.zsmb.rainbowcake.guardiandemo.ui.list.ListPresenter.NewsItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter : ListAdapter<NewsItem, NewsAdapter.ViewHolder>(NewsItemComparator) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.thumbnailImage
        private val headline: TextView = itemView.headlineText
        private val trail: TextView = itemView.trailText

        private var newsItem: NewsItem? = null

        init {
            itemView.setOnClickListener {
                newsItem?.let { listener?.onNewsItemSelected(it.id) }
            }
        }

        fun bind(item: NewsItem) {
            newsItem = item

            headline.text = item.headline
            trail.text = Html.fromHtml(item.trail)

            Glide.with(image)
                .load(item.thumbnailUrl)
                .into(image)
        }
    }

    interface Listener {
        fun onNewsItemSelected(id: String)
    }

}

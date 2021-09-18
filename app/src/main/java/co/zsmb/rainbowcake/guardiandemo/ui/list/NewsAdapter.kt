package co.zsmb.rainbowcake.guardiandemo.ui.list

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.guardiandemo.R
import co.zsmb.rainbowcake.guardiandemo.databinding.ItemNewsBinding
import co.zsmb.rainbowcake.guardiandemo.ui.list.ListPresenter.NewsItem
import coil.load

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
        private val binding = ItemNewsBinding.bind(itemView)

        private var newsItem: NewsItem? = null

        init {
            itemView.setOnClickListener {
                newsItem?.let { listener?.onNewsItemSelected(it.id) }
            }
        }

        fun bind(item: NewsItem) {
            newsItem = item

            binding.headlineText.text = item.headline
            binding.trailText.text = Html.fromHtml(item.trail)
            binding.thumbnailImage.load(item.thumbnailUrl)
        }
    }

    interface Listener {
        fun onNewsItemSelected(id: String)
    }

}

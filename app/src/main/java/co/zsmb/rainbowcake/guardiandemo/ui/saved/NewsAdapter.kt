package co.zsmb.rainbowcake.guardiandemo.ui.saved

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.guardiandemo.R
import co.zsmb.rainbowcake.guardiandemo.databinding.ItemSavedNewsBinding
import co.zsmb.rainbowcake.guardiandemo.ui.saved.SavedPresenter.SavedNewsItem
import coil.load

class SavedNewsAdapter :
    ListAdapter<SavedNewsItem, SavedNewsAdapter.ViewHolder>(SavedNewsItemComparator) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_saved_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSavedNewsBinding.bind(itemView)

        private var news: SavedNewsItem? = null

        init {
            itemView.setOnClickListener {
                news?.let {
                    listener?.onNewsItemSelected(it.id)
                }
            }
        }

        fun bind(item: SavedNewsItem) {
            news = item

            binding.headlineText.text = item.headline
            binding.trailText.text = Html.fromHtml(item.trail)
            binding.thumbnailImage.load(item.thumbnailUrl)
        }
    }

    interface Listener {
        fun onNewsItemSelected(newsId: String)
    }
}

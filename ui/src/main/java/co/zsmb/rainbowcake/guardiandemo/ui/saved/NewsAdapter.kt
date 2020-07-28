package co.zsmb.rainbowcake.guardiandemo.ui.saved

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.guardiandemo.presentation.saved.SavedPresenter.SavedNewsItem
import co.zsmb.rainbowcake.guardiandemo.ui.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

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
        val image: ImageView = itemView.thumbnailImage
        val headline: TextView = itemView.headlineText
        val trail: TextView = itemView.trailText

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

            headline.text = item.headline
            trail.text = Html.fromHtml(item.trail)

            Glide.with(image)
                .load(item.thumbnailUrl)
                .into(image)
        }
    }

    interface Listener {
        fun onNewsItemSelected(newsId: String)
    }

}

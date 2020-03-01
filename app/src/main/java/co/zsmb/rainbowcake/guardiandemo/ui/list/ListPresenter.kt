package co.zsmb.rainbowcake.guardiandemo.ui.list

import co.zsmb.rainbowcake.guardiandemo.domain.NewsInteractor
import co.zsmb.rainbowcake.withIOContext
import javax.inject.Inject

class ListPresenter @Inject constructor(
    private val newsInteractor: NewsInteractor
) {

    suspend fun getNewsItems(): List<NewsItem> = withIOContext {
        newsInteractor.getNewsItems().map { news ->
            NewsItem(
                id = news.id,
                headline = news.headline,
                trail = news.trail,
                thumbnailUrl = news.thumbnailUrl
            )
        }
    }

    data class NewsItem(
        val id: String,
        val headline: String,
        val trail: String,
        val thumbnailUrl: String?
    )

}

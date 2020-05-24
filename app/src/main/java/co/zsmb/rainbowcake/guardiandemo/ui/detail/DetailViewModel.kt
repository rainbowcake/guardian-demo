package co.zsmb.rainbowcake.guardiandemo.ui.detail

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import java.io.IOException
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val detailPresenter: DetailPresenter
) : RainbowCakeViewModel<DetailViewState>(Loading) {

    object SavedEvent : OneShotEvent
    object RemovedEvent : OneShotEvent
    object SaveFailedEvent : OneShotEvent
    object LoadFailedEvent : OneShotEvent

    fun loadArticle(articleId: String) = execute {
        val news = try {
            detailPresenter.loadArticle(articleId)
        } catch (e: IOException) {
            postEvent(LoadFailedEvent)
            return@execute
        }

        viewState = DetailReady(news)
    }

    fun toggleSaved(newsId: String) = execute {
        val state = viewState as? DetailReady ?: return@execute
        if (state.news.isSaved) {
            detailPresenter.removeArticle(newsId)
            viewState = state.copy(news = state.news.copy(isSaved = false))
            postEvent(RemovedEvent)
        } else {
            try {
                detailPresenter.saveArticle(newsId)
            } catch (e: IOException) {
                postEvent(SaveFailedEvent)
                return@execute
            }
            viewState = state.copy(news = state.news.copy(isSaved = true))
            postEvent(SavedEvent)
        }
    }

}

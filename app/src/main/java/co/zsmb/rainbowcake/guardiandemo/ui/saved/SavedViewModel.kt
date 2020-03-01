package co.zsmb.rainbowcake.guardiandemo.ui.saved

import co.zsmb.rainbowcake.base.JobViewModel
import co.zsmb.rainbowcake.guardiandemo.domain.News
import co.zsmb.rainbowcake.guardiandemo.ui.saved.SavedPresenter.SavedNewsItem
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SavedViewModel @Inject constructor(
    private val savedPresenter: SavedPresenter
) : JobViewModel<SavedViewState>(Loading) {

    init {
        executeNonBlocking {
            savedPresenter.getSavedNews().collect { news ->
                if (news.isEmpty()) {
                    viewState = Empty
                } else {
                    viewState = SavedReady(news.map(this::toSavedNewsItem))
                }
            }
        }
    }

    private fun toSavedNewsItem(news: News): SavedNewsItem {
        return SavedNewsItem(
            id = news.id,
            headline = news.headline,
            trail = news.trail,
            thumbnailUrl = news.thumbnailUrl
        )
    }

}

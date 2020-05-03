package co.zsmb.rainbowcake.guardiandemo.ui.saved

import co.zsmb.rainbowcake.guardiandemo.domain.News
import co.zsmb.rainbowcake.guardiandemo.domain.NewsInteractor
import co.zsmb.rainbowcake.withIOContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavedPresenter @Inject constructor(
    private val newsInteractor: NewsInteractor
) {

    suspend fun getSavedNews(): Flow<List<SavedNewsItem>> = withIOContext {
        newsInteractor.getSavedNews().map { news ->
            news.map(News::toSavedNewsItem)
        }
    }

    data class SavedNewsItem(
        val id: String,
        val headline: String,
        val trail: String,
        val thumbnailUrl: String?
    )

}

private fun News.toSavedNewsItem(): SavedPresenter.SavedNewsItem {
    return SavedPresenter.SavedNewsItem(
        id = id,
        headline = headline,
        trail = trail,
        thumbnailUrl = thumbnailUrl
    )
}

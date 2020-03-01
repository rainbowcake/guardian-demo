package co.zsmb.rainbowcake.guardiandemo.ui.saved

import co.zsmb.rainbowcake.guardiandemo.domain.News
import co.zsmb.rainbowcake.guardiandemo.domain.NewsInteractor
import co.zsmb.rainbowcake.withIOContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedPresenter @Inject constructor(
    private val newsInteractor: NewsInteractor
) {

    suspend fun getSavedNews(): Flow<List<News>> = withIOContext {
        newsInteractor.getSavedNewsItems()
    }

    data class SavedNewsItem(
        val id: String,
        val headline: String,
        val trail: String,
        val thumbnailUrl: String?
    )

}

package co.zsmb.rainbowcake.guardiandemo.ui.detail

import co.zsmb.rainbowcake.guardiandemo.domain.NewsInteractor
import co.zsmb.rainbowcake.withIOContext
import javax.inject.Inject

class DetailPresenter @Inject constructor(
    private val newsInteractor: NewsInteractor
) {

    suspend fun loadArticle(articleId: String): DetailedNewsItem = withIOContext {
        newsInteractor.getNewsItemById(articleId).let { news ->
            DetailedNewsItem(
                id = news.id,
                imageUrl = news.thumbnailUrl,
                title = news.headline,
                byline = news.trail,
                content = news.content,
                isSaved = newsInteractor.isSaved(articleId)
            )
        }
    }

    suspend fun saveArticle(newsId: String) = withIOContext {
        newsInteractor.saveNewsItem(newsId)
    }

    suspend fun removeArticle(newsId: String) = withIOContext {
        newsInteractor.removeSavedNewsItem(newsId)
    }

    data class DetailedNewsItem(
        val id: String,
        val imageUrl: String?,
        val title: String,
        val byline: String,
        val content: String,
        val isSaved: Boolean
    )

}

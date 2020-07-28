package co.zsmb.rainbowcake.guardiandemo.ui.detail

import co.zsmb.rainbowcake.guardiandemo.presentation.detail.DetailPresenter
import co.zsmb.rainbowcake.guardiandemo.presentation.detail.DetailPresenter.DetailedNewsItem
import co.zsmb.rainbowcake.guardiandemo.presentation.detail.DetailReady
import co.zsmb.rainbowcake.guardiandemo.presentation.detail.DetailViewModel
import co.zsmb.rainbowcake.guardiandemo.presentation.detail.Loading
import co.zsmb.rainbowcake.test.assertObserved
import co.zsmb.rainbowcake.test.base.ViewModelTest
import co.zsmb.rainbowcake.test.observeStateAndEvents
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest : ViewModelTest() {

    companion object {
        private const val MOCK_ARTICLE_ID = "saved_mock_id"

        private val SAVED_MOCK_DETAIL_ARTICLE = DetailedNewsItem(
            id = MOCK_ARTICLE_ID,
            title = "Article Title",
            imageUrl = "http://image.com/some-image.png",
            byline = "By Jane Doe",
            content = "This is the body of the article",
            isSaved = true
        )

        private val NON_SAVED_MOCK_DETAIL_ARTICLE = DetailedNewsItem(
            id = MOCK_ARTICLE_ID,
            title = "Article Title",
            imageUrl = "http://image.com/some-image.png",
            byline = "By Jane Doe",
            content = "This is the body of the article",
            isSaved = false
        )
    }

    @Test
    fun `Article is loaded correctly from presenter by ID`() = runBlockingTest {
        val detailPresenter: DetailPresenter = mock()
        whenever(detailPresenter.loadArticle(MOCK_ARTICLE_ID)) doReturn SAVED_MOCK_DETAIL_ARTICLE

        val vm = DetailViewModel(detailPresenter)

        vm.observeStateAndEvents { stateObserver, eventsObserver ->
            vm.loadArticle(MOCK_ARTICLE_ID)

            stateObserver.assertObserved(
                Loading,
                DetailReady(SAVED_MOCK_DETAIL_ARTICLE)
            )
        }
    }

    @Test
    fun `Article loading exception produces error event`() = runBlockingTest {
        val detailPresenter: DetailPresenter = mock()
        whenever(detailPresenter.loadArticle(any())).thenAnswer {
            throw IOException("Failed to load article")
        }

        val vm = DetailViewModel(detailPresenter)

        vm.observeStateAndEvents { stateObserver, eventsObserver ->
            vm.loadArticle(MOCK_ARTICLE_ID)

            eventsObserver.assertObserved(DetailViewModel.LoadFailedEvent)
        }
    }

    @Test
    fun `Toggling non-saved article saves article, updates view state and emits success event`() =
        runBlockingTest {
            val detailPresenter: DetailPresenter = mock()
            whenever(detailPresenter.loadArticle(MOCK_ARTICLE_ID)) doReturn NON_SAVED_MOCK_DETAIL_ARTICLE

            val vm = DetailViewModel(detailPresenter)

            vm.observeStateAndEvents { stateObserver, eventsObserver ->
                vm.loadArticle(MOCK_ARTICLE_ID)
                vm.toggleSaved(MOCK_ARTICLE_ID)

                stateObserver.assertObserved(
                    Loading,
                    DetailReady(NON_SAVED_MOCK_DETAIL_ARTICLE),
                    DetailReady(SAVED_MOCK_DETAIL_ARTICLE)
                )
                eventsObserver.assertObserved(DetailViewModel.SavedEvent)
            }
        }

    @Test
    fun `Toggling saved article removes article, updates view state and emits success event`() =
        runBlockingTest {
            val detailPresenter: DetailPresenter = mock()
            whenever(detailPresenter.loadArticle(MOCK_ARTICLE_ID)) doReturn SAVED_MOCK_DETAIL_ARTICLE

            val vm = DetailViewModel(detailPresenter)

            vm.observeStateAndEvents { stateObserver, eventsObserver ->
                vm.loadArticle(MOCK_ARTICLE_ID)
                vm.toggleSaved(MOCK_ARTICLE_ID)

                stateObserver.assertObserved(
                    Loading,
                    DetailReady(SAVED_MOCK_DETAIL_ARTICLE),
                    DetailReady(NON_SAVED_MOCK_DETAIL_ARTICLE)
                )
                eventsObserver.assertObserved(DetailViewModel.RemovedEvent)
            }
        }

    @Test
    fun `Exception while saving article produces error event`() = runBlockingTest {
        val detailPresenter: DetailPresenter = mock()
        whenever(detailPresenter.loadArticle(MOCK_ARTICLE_ID)) doReturn NON_SAVED_MOCK_DETAIL_ARTICLE
        whenever(detailPresenter.saveArticle(any())).thenAnswer {
            throw IOException("Failed to save article")
        }

        val vm = DetailViewModel(detailPresenter)

        vm.observeStateAndEvents { stateObserver, eventsObserver ->
            vm.loadArticle(MOCK_ARTICLE_ID)
            vm.toggleSaved(MOCK_ARTICLE_ID)

            eventsObserver.assertObserved(DetailViewModel.SaveFailedEvent)
        }
    }

}

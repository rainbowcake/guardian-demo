package co.zsmb.rainbowcake.guardiandemo.ui.list

import co.zsmb.rainbowcake.guardiandemo.presentation.list.*
import co.zsmb.rainbowcake.guardiandemo.presentation.list.ListPresenter.NewsItem
import co.zsmb.rainbowcake.test.assertObserved
import co.zsmb.rainbowcake.test.base.ViewModelTest
import co.zsmb.rainbowcake.test.observeStateAndEvents
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest : ViewModelTest() {

    companion object {
        private val MOCK_NEWS_ITEMS = listOf(
            NewsItem(
                id = "news-item-1",
                headline = "Title is important",
                thumbnailUrl = "http://image.com/some-image.png",
                trail = "Some very enticing details about the article in a sentence or two"
            ),
            NewsItem(
                id = "news-item-2",
                headline = "Title is important 2",
                thumbnailUrl = "http://image.com/some-image-2.png",
                trail = "Certainly important additional details about the article"
            )
        )
    }

    @Test
    fun `News items are loaded correctly from presenter upon creation`() = runBlockingTest {
        val listPresenter: ListPresenter = mock()
        whenever(listPresenter.getNewsItems()).doReturn(MOCK_NEWS_ITEMS)

        val vm = ListViewModel(listPresenter)

        vm.observeStateAndEvents { stateObserver, eventsObserver ->
            stateObserver.assertObserved(
                ListReady(MOCK_NEWS_ITEMS)
            )
        }
    }

    @Test
    fun `Presenter error leads to error state upon creation`() = runBlockingTest {
        val listPresenter: ListPresenter = mock()
        whenever(listPresenter.getNewsItems()).thenAnswer {
            throw IOException("No internet available")
        }

        val vm = ListViewModel(listPresenter)

        vm.observeStateAndEvents { stateObserver, eventsObserver ->
            stateObserver.assertObserved(
                NetworkError
            )
        }
    }

    @Test
    fun `Reload after error can load items correctly`() = runBlockingTest {
        val listPresenter: ListPresenter = mock()
        var invocations = 0
        whenever(listPresenter.getNewsItems()).thenAnswer {
            when (invocations++) {
                0 -> throw IOException("Network error")
                else -> MOCK_NEWS_ITEMS
            }
        }

        val vm = ListViewModel(listPresenter)

        vm.observeStateAndEvents { stateObserver, eventsObserver ->
            vm.reload()

            stateObserver.assertObserved(
                NetworkError,
                Loading,
                ListReady(MOCK_NEWS_ITEMS)
            )
        }
    }

}

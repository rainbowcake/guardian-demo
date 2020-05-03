package co.zsmb.rainbowcake.guardiandemo.ui.saved

import co.zsmb.rainbowcake.guardiandemo.ui.saved.SavedPresenter.SavedNewsItem
import co.zsmb.rainbowcake.test.assertObserved
import co.zsmb.rainbowcake.test.base.ViewModelTest
import co.zsmb.rainbowcake.test.observeStateAndEvents
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SavedViewModelTest : ViewModelTest() {

    companion object {
        private val MOCK_SAVED_ITEMS = listOf(
            SavedNewsItem(
                id = "news-item-1",
                headline = "Title is important",
                thumbnailUrl = "http://image.com/some-image.png",
                trail = "Some very enticing details about the article in a sentence or two"
            ),
            SavedNewsItem(
                id = "news-item-2",
                headline = "Title is important 2",
                thumbnailUrl = "http://image.com/some-image-2.png",
                trail = "Certainly important additional details about the article"
            )
        )
        private val MOCK_NO_SAVED_ITEMS = emptyList<SavedNewsItem>()
    }

    @Test
    fun `News items are loaded correctly from presenter upon creation`() = runBlockingTest {
        val savedPresenter: SavedPresenter = mock()
        whenever(savedPresenter.getSavedNews()).doReturn(flowOf(MOCK_SAVED_ITEMS))

        val vm = SavedViewModel(savedPresenter)

        vm.observeStateAndEvents { stateObserver, eventsObserver ->
            stateObserver.assertObserved(SavedReady(MOCK_SAVED_ITEMS))
        }
    }

    @Test
    fun `Empty state is displayed when there are no news items loaded upon creation`() =
        runBlockingTest {
            val savedPresenter: SavedPresenter = mock()
            whenever(savedPresenter.getSavedNews()).doReturn(flowOf(MOCK_NO_SAVED_ITEMS))

            val vm = SavedViewModel(savedPresenter)

            vm.observeStateAndEvents { stateObserver, eventsObserver ->
                stateObserver.assertObserved(Empty)
            }
        }

}

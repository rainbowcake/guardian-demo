package co.zsmb.rainbowcake.guardiandemo.ui.saved

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.guardiandemo.presentation.saved.*
import co.zsmb.rainbowcake.guardiandemo.ui.R
import co.zsmb.rainbowcake.guardiandemo.ui.detail.DetailFragment
import co.zsmb.rainbowcake.guardiandemo.ui.list.ListFragment
import co.zsmb.rainbowcake.navigation.BackPressAware
import co.zsmb.rainbowcake.navigation.navigator
import co.zsmb.rainbowcake.navigation.popUntil
import kotlinx.android.synthetic.main.fragment_list.newsList
import kotlinx.android.synthetic.main.fragment_saved.*

class SavedFragment : RainbowCakeFragment<SavedViewState, SavedViewModel>(),
    SavedNewsAdapter.Listener, BackPressAware {

    private object Flipper {
        const val EMPTY = 0
        const val CONTENT = 1
        const val LOADING = 2
    }

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_saved

    private lateinit var savedNewsAdapter: SavedNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedNewsAdapter = SavedNewsAdapter()
        savedNewsAdapter.listener = this
        newsList.adapter = savedNewsAdapter
    }

    override fun render(viewState: SavedViewState) {
        when (viewState) {
            Loading -> {
                viewFlipper.displayedChild = Flipper.LOADING
            }
            is SavedReady -> {
                viewFlipper.displayedChild = Flipper.CONTENT
                savedNewsAdapter.submitList(viewState.news)
            }
            Empty -> {
                viewFlipper.displayedChild = Flipper.EMPTY
            }
        }.exhaustive
    }

    override fun onNewsItemSelected(newsId: String) {
        navigator?.add(DetailFragment.newInstance(newsId))
    }

    override fun onBackPressed(): Boolean {
        return navigator?.popUntil<ListFragment>() ?: false
    }

}

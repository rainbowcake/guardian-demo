package co.zsmb.rainbowcake.guardiandemo.ui.saved

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.guardiandemo.R
import co.zsmb.rainbowcake.guardiandemo.databinding.FragmentSavedBinding
import co.zsmb.rainbowcake.guardiandemo.ui.detail.DetailFragment
import co.zsmb.rainbowcake.guardiandemo.ui.list.ListFragment
import co.zsmb.rainbowcake.navigation.BackPressAware
import co.zsmb.rainbowcake.navigation.navigator
import co.zsmb.rainbowcake.navigation.popUntil

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

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSavedBinding.bind(view)

        savedNewsAdapter = SavedNewsAdapter()
        savedNewsAdapter.listener = this
        binding.newsList.adapter = savedNewsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun render(viewState: SavedViewState) {
        when (viewState) {
            Loading -> {
                binding.viewFlipper.displayedChild = Flipper.LOADING
            }
            is SavedReady -> {
                binding.viewFlipper.displayedChild = Flipper.CONTENT
                savedNewsAdapter.submitList(viewState.news)
            }
            Empty -> {
                binding.viewFlipper.displayedChild = Flipper.EMPTY
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

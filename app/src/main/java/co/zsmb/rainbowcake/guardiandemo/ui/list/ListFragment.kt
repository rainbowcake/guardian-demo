package co.zsmb.rainbowcake.guardiandemo.ui.list

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.guardiandemo.R
import co.zsmb.rainbowcake.guardiandemo.databinding.FragmentListBinding
import co.zsmb.rainbowcake.guardiandemo.ui.detail.DetailFragment
import co.zsmb.rainbowcake.guardiandemo.ui.saved.SavedFragment
import co.zsmb.rainbowcake.navigation.navigator

class ListFragment : RainbowCakeFragment<ListViewState, ListViewModel>(), NewsAdapter.Listener {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_list

    private lateinit var newsAdapter: NewsAdapter

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)

        newsAdapter = NewsAdapter()
        newsAdapter.listener = this
        binding.newsList.adapter = newsAdapter
        binding.newsList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.retryButton.setOnClickListener {
            viewModel.reload()
        }
        binding.viewSavedButton.setOnClickListener {
            navigator?.add(SavedFragment())
        }

        binding.toolbar.inflateMenu(R.menu.menu_list)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_view_saved -> {
                    navigator?.add(SavedFragment())
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun render(viewState: ListViewState) {
        TransitionManager.beginDelayedTransition(binding.listFragmentRoot)
        when (viewState) {
            Loading -> {
                binding.loadingIndicator.isVisible = true
                binding.newsList.isVisible = false
                binding.errorGroup.isVisible = false
            }
            is ListReady -> {
                newsAdapter.submitList(viewState.news)
                binding.loadingIndicator.isVisible = false
                binding.newsList.isVisible = true
                binding.errorGroup.isVisible = false
            }
            NetworkError -> {
                newsAdapter.submitList(null)
                binding.loadingIndicator.isVisible = false
                binding.newsList.isVisible = false
                binding.errorGroup.isVisible = true
            }
        }.exhaustive
    }

    override fun onNewsItemSelected(id: String) {
        navigator?.add(DetailFragment.newInstance(id))
    }

}

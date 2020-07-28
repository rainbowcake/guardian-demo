package co.zsmb.rainbowcake.guardiandemo.ui.list

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.guardiandemo.presentation.list.*
import co.zsmb.rainbowcake.guardiandemo.ui.R
import co.zsmb.rainbowcake.guardiandemo.ui.detail.DetailFragment
import co.zsmb.rainbowcake.guardiandemo.ui.saved.SavedFragment
import co.zsmb.rainbowcake.navigation.navigator
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : RainbowCakeFragment<ListViewState, ListViewModel>(), NewsAdapter.Listener {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_list

    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsAdapter()
        newsAdapter.listener = this
        newsList.adapter = newsAdapter
        newsList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        retryButton.setOnClickListener {
            viewModel.reload()
        }
        viewSavedButton.setOnClickListener {
            navigator?.add(SavedFragment())
        }

        toolbar.inflateMenu(R.menu.menu_list)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_view_saved -> {
                    navigator?.add(SavedFragment())
                }
            }
            true
        }
    }

    override fun render(viewState: ListViewState) {
        TransitionManager.beginDelayedTransition(listFragmentRoot)
        when (viewState) {
            Loading -> {
                loadingIndicator.isVisible = true
                newsList.isVisible = false
                errorGroup.isVisible = false
            }
            is ListReady -> {
                newsAdapter.submitList(viewState.news)
                loadingIndicator.isVisible = false
                newsList.isVisible = true
                errorGroup.isVisible = false
            }
            NetworkError -> {
                newsAdapter.submitList(null)
                loadingIndicator.isVisible = false
                newsList.isVisible = false
                errorGroup.isVisible = true
            }
        }.exhaustive
    }

    override fun onNewsItemSelected(id: String) {
        navigator?.add(DetailFragment.newInstance(id))
    }

}

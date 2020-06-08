package co.zsmb.rainbowcake.guardiandemo.ui.detail

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.guardiandemo.R
import co.zsmb.rainbowcake.guardiandemo.ui.detail.DetailViewModel.*
import co.zsmb.rainbowcake.guardiandemo.ui.saved.SavedFragment
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.extensions.requireString
import co.zsmb.rainbowcake.navigation.navigator
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : RainbowCakeFragment<DetailViewState, DetailViewModel>() {

    companion object {
        private const val ARG_NEWS_ID = "ARG_NEWS_ID"

        fun newInstance(newsId: String): DetailFragment {
            return DetailFragment().applyArgs {
                putString(ARG_NEWS_ID, newsId)
            }
        }
    }

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_detail

    private lateinit var newsId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()

        toolbar.inflateMenu(R.menu.menu_detail_saved)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_save -> {
                    viewModel.toggleSaved(newsId)
                }
            }
            true
        }
    }

    private fun initArgs() {
        newsId = requireArguments().requireString(ARG_NEWS_ID)
    }

    override fun onStart() {
        super.onStart()

        viewModel.loadArticle(newsId)
    }

    override fun render(viewState: DetailViewState) {
        when (viewState) {
            Loading -> {
            }
            is DetailReady -> {
                val news = viewState.news

                titleText.text = news.title
                byline.text = Html.fromHtml(news.byline)
                contentText.text = Html.fromHtml(news.content)

                Glide.with(articleImage)
                    .load(news.imageUrl)
                    .into(articleImage)

                val iconRes = if (news.isSaved) R.drawable.ic_star else R.drawable.ic_star_border
                toolbar.menu.getItem(0).setIcon(iconRes)
            }
        }
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            LoadFailedEvent -> {
                Toast.makeText(
                    requireContext(),
                    R.string.detail_error_load_failed,
                    Toast.LENGTH_SHORT
                )
                    .show()
                navigator?.pop()
            }
            SavedEvent -> {
                Snackbar.make(
                    detailFragmentRoot,
                    R.string.detail_save_success,
                    Snackbar.LENGTH_SHORT
                )
                    .setAction(R.string.detail_save_success_action) {
                        navigator?.add(SavedFragment())
                    }
                    .show()
            }
            RemovedEvent -> {
                Snackbar.make(
                    detailFragmentRoot,
                    R.string.detail_remove_success,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            SaveFailedEvent -> {
                Snackbar.make(
                    detailFragmentRoot,
                    R.string.detail_save_failed,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

}

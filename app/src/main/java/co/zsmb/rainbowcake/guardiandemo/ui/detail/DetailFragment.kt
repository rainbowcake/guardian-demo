package co.zsmb.rainbowcake.guardiandemo.ui.detail

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.guardiandemo.R
import co.zsmb.rainbowcake.guardiandemo.databinding.FragmentDetailBinding
import co.zsmb.rainbowcake.guardiandemo.ui.detail.DetailViewModel.*
import co.zsmb.rainbowcake.guardiandemo.ui.saved.SavedFragment
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.extensions.requireString
import co.zsmb.rainbowcake.navigation.navigator
import coil.load
import com.google.android.material.snackbar.Snackbar

class DetailFragment : RainbowCakeFragment<DetailViewState, DetailViewModel>() {

    companion object {
        private const val ARG_NEWS_ID = "ARG_NEWS_ID"

        fun newInstance(newsId: String): DetailFragment {
            return DetailFragment().applyArgs {
                putString(ARG_NEWS_ID, newsId)
            }
        }
    }

    private lateinit var newsId: String

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_detail

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()

        _binding = FragmentDetailBinding.bind(view)

        binding.toolbar.inflateMenu(R.menu.menu_detail_saved)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_save -> {
                    viewModel.toggleSaved(newsId)
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

                binding.titleText.text = news.title
                binding.byline.text = Html.fromHtml(news.byline)
                binding.contentText.text = Html.fromHtml(news.content)
                binding.articleImage.load(news.imageUrl)

                val iconRes = if (news.isSaved) R.drawable.ic_star else R.drawable.ic_star_border
                binding.toolbar.menu.getItem(0).setIcon(iconRes)
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
                    binding.detailFragmentRoot,
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
                    binding.detailFragmentRoot,
                    R.string.detail_remove_success,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            SaveFailedEvent -> {
                Snackbar.make(
                    binding.detailFragmentRoot,
                    R.string.detail_save_failed,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

}

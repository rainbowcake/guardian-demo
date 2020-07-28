package co.zsmb.rainbowcake.guardiandemo.presentation.saved

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SavedViewModel @Inject constructor(
    private val savedPresenter: SavedPresenter
) : RainbowCakeViewModel<SavedViewState>(Loading) {

    init {
        executeNonBlocking {
            savedPresenter.getSavedNews().collect { news ->
                viewState = if (news.isEmpty()) {
                    Empty
                } else {
                    SavedReady(news)
                }
            }
        }
    }

}

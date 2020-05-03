package co.zsmb.rainbowcake.guardiandemo.ui.saved

import co.zsmb.rainbowcake.base.JobViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SavedViewModel @Inject constructor(
    private val savedPresenter: SavedPresenter
) : JobViewModel<SavedViewState>(Loading) {

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

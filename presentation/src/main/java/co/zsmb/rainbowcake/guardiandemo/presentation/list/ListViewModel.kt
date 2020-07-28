package co.zsmb.rainbowcake.guardiandemo.presentation.list

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import java.io.IOException
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val listPresenter: ListPresenter
) : RainbowCakeViewModel<ListViewState>(Loading) {

    init {
        execute { loadNews() }
    }

    fun reload() {
        execute { loadNews() }
    }

    private suspend fun loadNews() {
        viewState = Loading
        viewState = try {
            val items = listPresenter.getNewsItems()
            ListReady(items)
        } catch (e: IOException) {
            NetworkError
        }
    }

}

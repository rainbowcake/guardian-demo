package co.zsmb.rainbowcake.guardiandemo.ui.list

import co.zsmb.rainbowcake.base.JobViewModel
import java.io.IOException
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val listPresenter: ListPresenter
) : JobViewModel<ListViewState>(Loading) {

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

package co.zsmb.rainbowcake.guardiandemo.ui.list

import co.zsmb.rainbowcake.guardiandemo.ui.list.ListPresenter.NewsItem

sealed class ListViewState

object Loading : ListViewState()

data class ListReady(val news: List<NewsItem>) : ListViewState()

object NetworkError : ListViewState()

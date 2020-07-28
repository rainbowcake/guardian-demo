package co.zsmb.rainbowcake.guardiandemo.presentation.list

sealed class ListViewState

object Loading : ListViewState()

data class ListReady(val news: List<ListPresenter.NewsItem>) : ListViewState()

object NetworkError : ListViewState()

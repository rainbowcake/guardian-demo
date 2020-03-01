package co.zsmb.rainbowcake.guardiandemo.ui.detail

sealed class DetailViewState

object Loading : DetailViewState()

data class DetailReady(val news: DetailPresenter.DetailedNewsItem) : DetailViewState()

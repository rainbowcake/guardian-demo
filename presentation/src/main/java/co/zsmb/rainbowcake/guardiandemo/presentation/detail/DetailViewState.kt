package co.zsmb.rainbowcake.guardiandemo.presentation.detail

sealed class DetailViewState

object Loading : DetailViewState()

data class DetailReady(val news: DetailPresenter.DetailedNewsItem) : DetailViewState()

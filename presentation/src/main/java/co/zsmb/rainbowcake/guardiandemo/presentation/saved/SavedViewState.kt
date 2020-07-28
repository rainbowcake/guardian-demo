package co.zsmb.rainbowcake.guardiandemo.presentation.saved

sealed class SavedViewState

object Loading : SavedViewState()

data class SavedReady(val news: List<SavedPresenter.SavedNewsItem>) : SavedViewState()

object Empty : SavedViewState()

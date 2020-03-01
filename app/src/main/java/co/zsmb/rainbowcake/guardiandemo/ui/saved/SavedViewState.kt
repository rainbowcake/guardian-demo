package co.zsmb.rainbowcake.guardiandemo.ui.saved

import co.zsmb.rainbowcake.guardiandemo.ui.saved.SavedPresenter.SavedNewsItem

sealed class SavedViewState

object Loading : SavedViewState()

data class SavedReady(val news: List<SavedNewsItem>) : SavedViewState()

object Empty : SavedViewState()

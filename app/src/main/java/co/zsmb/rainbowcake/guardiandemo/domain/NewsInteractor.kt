package co.zsmb.rainbowcake.guardiandemo.domain

import co.zsmb.rainbowcake.guardiandemo.data.disk.DiskDataSource
import co.zsmb.rainbowcake.guardiandemo.data.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val diskDataSource: DiskDataSource
) {

    suspend fun getNewsItems(): List<News> {
        return networkDataSource.getNewsItems()
    }

    fun getSavedNews(): Flow<List<News>> {
        return diskDataSource.getSavedNews()
    }

    suspend fun saveNewsItem(id: String) {
        val item = requireNotNull(networkDataSource.getNewsItemById(id))
        diskDataSource.saveNewsItem(item)
    }

    suspend fun removeSavedNewsItem(id: String) {
        diskDataSource.removeSavedNewsItem(id)
    }

    suspend fun getNewsItemById(id: String): News {
        return diskDataSource.getSavedNewsItemById(id) ?: networkDataSource.getNewsItemById(id)
    }

    suspend fun isSaved(id: String): Boolean {
        return diskDataSource.hasNewsItem(id)
    }

}

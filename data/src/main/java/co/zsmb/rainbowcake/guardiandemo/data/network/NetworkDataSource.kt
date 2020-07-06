package co.zsmb.rainbowcake.guardiandemo.data.network

import co.zsmb.rainbowcake.guardiandemo.News
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSource @Inject constructor(
    private val guardianApi: GuardianApi
) {

    suspend fun getNewsItems(): List<News> {
        return guardianApi.getNews().response.results
            .filter { it.fields.thumbnail != null }
            .map { newsContent ->
                News(
                    id = newsContent.id,
                    headline = newsContent.fields.headline,
                    trail = newsContent.fields.trailText,
                    thumbnailUrl = newsContent.fields.thumbnail!!,
                    content = newsContent.fields.body
                )
            }
    }

    suspend fun getNewsItemById(id: String): News {
        return guardianApi.getNewsById(id).response.content.let { newsContent ->
            News(
                id = newsContent.id,
                headline = newsContent.fields.headline,
                trail = newsContent.fields.trailText,
                thumbnailUrl = newsContent.fields.thumbnail,
                content = newsContent.fields.body
            )
        }
    }

}

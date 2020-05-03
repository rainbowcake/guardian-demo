package co.zsmb.rainbowcake.guardiandemo.data.network

import kotlinx.coroutines.runBlocking
import org.junit.Test

class BasicApiTest {

    private val guardianApi: GuardianApi =
        DaggerNetworkTestComponent.create().guardianApi()

    @Test
    fun `Check that API responds and response is parsed to models without error`() = runBlocking {
        val news = guardianApi.getNews()

        assert(news.response.results.isNotEmpty())
    }

}

package co.zsmb.rainbowcake.guardiandemo.remote

import co.zsmb.rainbowcake.guardiandemo.remote.model.NewsResponseWrapper
import co.zsmb.rainbowcake.guardiandemo.remote.model.SearchResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Path

interface GuardianApi {
    @GET("search")
    suspend fun getNews(): SearchResponseWrapper

    @GET("{id}")
    suspend fun getNewsById(@Path("id", encoded = true) id: String): NewsResponseWrapper
}

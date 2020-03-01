package co.zsmb.rainbowcake.guardiandemo.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NewsResponseWrapper(
    val response: NewsResponse
)

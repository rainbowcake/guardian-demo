package co.zsmb.rainbowcake.guardiandemo.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NewsResponse(
    val content: NewsContent
)

package co.zsmb.rainbowcake.guardiandemo.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SearchResponse(
    val status: String,
    val results: List<NewsContent>
)

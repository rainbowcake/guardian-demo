package co.zsmb.rainbowcake.guardiandemo.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NewsContent(
    val id: String,
    val type: String,
    val sectionName: String,
    val webTitle: String,
    val webUrl: String,
    val fields: NewsFields,
    val pillarName: String?
)

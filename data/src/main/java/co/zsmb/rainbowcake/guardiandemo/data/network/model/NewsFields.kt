package co.zsmb.rainbowcake.guardiandemo.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NewsFields(
    val wordcount: Int,
    val charCount: Int,
    val publication: String,
    val thumbnail: String?,
    val headline: String,
    val trailText: String,
    val body: String
)

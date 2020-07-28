package co.zsmb.rainbowcake.guardiandemo.remote.interceptor

import okhttp3.HttpUrl

class FieldsInterceptor : UrlInterceptor() {
    companion object {
        private val fields = listOf(
            "trailText",
            "headline",
            "thumbnail",
            "publication",
            "starRating",
            "charCount",
            "wordcount",
            "body"
        )
        private val fieldsStringList = fields.joinToString(separator = ",")
    }

    override fun HttpUrl.Builder.update(url: HttpUrl) {
        addQueryParameter("show-fields", fieldsStringList)
    }

}

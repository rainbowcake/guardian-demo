package co.zsmb.rainbowcake.guardiandemo.remote.interceptor

import okhttp3.HttpUrl

class SearchQueryInterceptor : UrlInterceptor() {

    companion object {
        private const val PAGE_SIZE = 50
    }

    override fun HttpUrl.Builder.update(url: HttpUrl) {
        if (url.encodedPath.contains("search")) {
            addQueryParameter("page-size", PAGE_SIZE.toString())
        }
    }

}

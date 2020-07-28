package co.zsmb.rainbowcake.guardiandemo.remote.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

abstract class UrlInterceptor : Interceptor {

    final override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        val newRequest = request.newBuilder()
            .url(
                url.newBuilder()
                    .apply {
                        update(url)
                    }
                    .build()
            )
            .build()
        return chain.proceed(newRequest)
    }

    protected abstract fun HttpUrl.Builder.update(url: HttpUrl): Unit

}

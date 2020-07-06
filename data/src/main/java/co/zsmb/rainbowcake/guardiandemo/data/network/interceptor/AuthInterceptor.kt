package co.zsmb.rainbowcake.guardiandemo.data.network.interceptor

import co.zsmb.rainbowcake.guardiandemo.data.BuildConfig
import okhttp3.HttpUrl

class AuthInterceptor : UrlInterceptor() {
    override fun HttpUrl.Builder.update(url: HttpUrl) {
        addQueryParameter("api-key", BuildConfig.GUARDIAN_API_KEY)
    }
}

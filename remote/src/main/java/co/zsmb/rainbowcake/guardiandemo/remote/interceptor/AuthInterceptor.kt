package co.zsmb.rainbowcake.guardiandemo.remote.interceptor

import co.zsmb.rainbowcake.guardiandemo.remote.BuildConfig
import okhttp3.HttpUrl

class AuthInterceptor : UrlInterceptor() {
    override fun HttpUrl.Builder.update(url: HttpUrl) {
        addQueryParameter("api-key", BuildConfig.GUARDIAN_API_KEY)
    }
}

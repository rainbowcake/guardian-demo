package co.zsmb.rainbowcake.guardiandemo.remote

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class]
)
interface NetworkTestComponent {
    fun guardianApi(): GuardianApi
}

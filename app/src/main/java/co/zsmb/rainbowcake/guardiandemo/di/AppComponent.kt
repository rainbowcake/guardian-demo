package co.zsmb.rainbowcake.guardiandemo.di

import co.zsmb.rainbowcake.dagger.RainbowCakeComponent
import co.zsmb.rainbowcake.dagger.RainbowCakeModule
import co.zsmb.rainbowcake.guardiandemo.local.DiskModule
import co.zsmb.rainbowcake.guardiandemo.remote.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RainbowCakeModule::class,
        ApplicationModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        DiskModule::class
    ]
)
interface AppComponent : RainbowCakeComponent

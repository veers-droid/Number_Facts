package com.example.numberfacts.dagger

import android.app.Application
import com.example.numberfacts.App
import com.example.numberfacts.ui.MainActivityCompose
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
ViewModelModule::class,
NetworkModule::class,
AppModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Application):AppComponent
    }

    fun inject(app: App)

    fun inject(activity : MainActivityCompose)
}
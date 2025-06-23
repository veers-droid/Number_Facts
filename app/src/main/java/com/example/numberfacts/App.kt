package com.example.numberfacts

import android.app.Application
import com.example.numberfacts.dagger.AppComponent
import com.example.numberfacts.dagger.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)

        appComponent.inject(this)
    }

}
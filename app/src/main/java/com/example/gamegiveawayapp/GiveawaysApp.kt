package com.example.gamegiveawayapp

import android.app.Application
import com.example.gamegiveawayapp.di.applicationModule
import com.example.gamegiveawayapp.di.networkModule
import com.example.gamegiveawayapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GiveawaysApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GiveawaysApp)
            modules(listOf(networkModule, applicationModule, viewModelModule))
        }
    }
}
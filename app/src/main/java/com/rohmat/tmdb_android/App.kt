package com.rohmat.tmdb_android

import android.app.Application
import com.rohmat.tmdb_android.di.databaseModule
import com.rohmat.tmdb_android.di.networkModule
import com.rohmat.tmdb_android.di.repositoryModule
import com.rohmat.tmdb_android.di.useCaseModule
import com.rohmat.tmdb_android.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
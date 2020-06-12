package com.abrahamlay.movielicious

import android.app.Application
import com.abrahamlay.movielicious.module.apiModule
import com.abrahamlay.movielicious.module.dataModule
import com.abrahamlay.movielicious.module.useCaseModule
import com.abrahamlay.movielicious.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Abraham Lay on 2020-06-09.
 */
class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidApplication)
            modules(
                listOf(
                    dataModule,
                    apiModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
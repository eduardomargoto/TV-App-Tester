package com.apptester.tv

import android.app.Application
import com.apptester.tv.domain.credentials.credentialsModule
import com.apptester.tv.presentation.ui.authentication.loginModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    val applicationScope = CoroutineScope(Job())

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                appModule,
                loginModule,
                credentialsModule
            )
        }
    }

    val appModule = module {
        factory { applicationScope }
    }
}
package com.apptester.tv

import android.app.Application
import androidx.credentials.CredentialManager
import com.apptester.tv.data.network.CredentialsRepository
import com.apptester.tv.data.network.CredentialsRepositoryImpl
import com.apptester.tv.domain.credentials.GoogleSignInUseCase
import com.apptester.tv.domain.credentials.GoogleSignOutUseCase
import com.apptester.tv.domain.credentials.RequestGoogleSignInUseCase
import com.apptester.tv.presentation.ui.authentication.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind

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
                authModule,
                credentialsModule,
            )
        }
    }

    val credentialsModule = module {
        factory<CredentialManager> {
            CredentialManager.create(androidApplication())
        }

        factoryOf(::CredentialsRepositoryImpl)
            .bind<CredentialsRepository>()

        factoryOf(::GoogleSignInUseCase)
        factoryOf(::GoogleSignOutUseCase)
        factoryOf(::RequestGoogleSignInUseCase)
    }

    val authModule = module {
        viewModelOf(::AuthViewModel)
    }

    val appModule = module {
        factory { applicationScope }
    }
}
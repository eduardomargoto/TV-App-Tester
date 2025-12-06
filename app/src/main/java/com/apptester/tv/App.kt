package com.apptester.tv

import android.app.Application
import androidx.credentials.CredentialManager
import com.apptester.tv.data.local.AuthDataStore
import com.apptester.tv.data.repository.CredentialsRepositoryImpl
import com.apptester.tv.data.networkModule
import com.apptester.tv.data.repository.AppDistributionRepositoryImpl
import com.apptester.tv.data.repository.OAuth2RepositoryImpl
import com.apptester.tv.domain.usecases.appdistribution.AppDistributionGetAppsUseCase
import com.apptester.tv.domain.usecases.appdistribution.AppDistributionGetProjectsUseCase
import com.apptester.tv.domain.usecases.appdistribution.AppDistributionGetReleasesUseCase
import com.apptester.tv.domain.usecases.credentials.GoogleSignInUseCase
import com.apptester.tv.domain.usecases.credentials.GoogleSignOutUseCase
import com.apptester.tv.domain.usecases.credentials.RequestGoogleSignInUseCase
import com.apptester.tv.domain.repository.AppDistributionRepository
import com.apptester.tv.domain.repository.CredentialsRepository
import com.apptester.tv.domain.repository.OAuth2Repository
import com.apptester.tv.domain.usecases.authentication.LoadTokensUseCase
import com.apptester.tv.domain.usecases.authentication.RefreshTokensUseCase
import com.apptester.tv.presentation.ui.appselection.AppSelectionViewModel
import com.apptester.tv.presentation.ui.authentication.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
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
                networkModule,
                repositoriesModule,
                credentialsModule,
                userCasesModule,
                viewModelsModule,
            )
        }
    }

    val repositoriesModule = module {
        factoryOf(::AppDistributionRepositoryImpl)
            .bind<AppDistributionRepository>()
        factoryOf(::CredentialsRepositoryImpl)
            .bind<CredentialsRepository>()
        factoryOf(::OAuth2RepositoryImpl)
            .bind<OAuth2Repository>()
    }

    val userCasesModule = module {
        // appdistribution
        factoryOf(::AppDistributionGetProjectsUseCase)
        factoryOf(::AppDistributionGetAppsUseCase)
        factoryOf(::AppDistributionGetReleasesUseCase)

        // credentials
        factoryOf(::GoogleSignInUseCase)
        factoryOf(::GoogleSignOutUseCase)
        factoryOf(::RequestGoogleSignInUseCase)

        // authentication
        factoryOf(::LoadTokensUseCase)
        factoryOf(::RefreshTokensUseCase)
    }

    val credentialsModule = module {
        factory<CredentialManager> {
            CredentialManager.create(androidApplication())
        }
    }

    val viewModelsModule = module {
        viewModelOf(::AuthViewModel)
        viewModelOf(::AppSelectionViewModel)
    }

    val appModule = module {
        factory { applicationScope }
        singleOf(::AuthDataStore)
    }
}
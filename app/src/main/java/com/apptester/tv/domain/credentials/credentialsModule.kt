package com.apptester.tv.domain.credentials

import androidx.credentials.CredentialManager
import com.apptester.tv.data.network.CredentialsRepository
import com.apptester.tv.data.network.CredentialsRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

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
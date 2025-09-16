package com.apptester.tv.presentation.features.authentication

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {

    viewModelOf(::AuthViewModel)
}
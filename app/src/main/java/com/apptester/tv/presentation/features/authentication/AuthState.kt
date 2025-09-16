package com.apptester.tv.presentation.features.authentication

import com.apptester.tv.domain.credentials.UserCredentials

sealed class AuthState {
    object Unauthenticated : AuthState()
    data class Authenticated(val credentials: UserCredentials) : AuthState()
    data class Error(val message: String) : AuthState()
}
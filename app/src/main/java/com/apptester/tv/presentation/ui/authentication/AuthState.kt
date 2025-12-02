package com.apptester.tv.presentation.ui.authentication

import com.apptester.tv.domain.UserCredentials

sealed class AuthState {
    object Unauthenticated : AuthState()
    data class Authenticated(val credentials: UserCredentials) : AuthState()
    data class Error(val message: String) : AuthState()
    object Loading : AuthState()
}
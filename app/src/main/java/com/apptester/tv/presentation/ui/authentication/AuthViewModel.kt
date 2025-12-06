package com.apptester.tv.presentation.ui.authentication

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apptester.tv.domain.usecases.authentication.LoadTokensUseCase
import com.apptester.tv.domain.usecases.credentials.GoogleSignInUseCase
import com.apptester.tv.domain.usecases.credentials.RequestGoogleSignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    val googleSignInUseCase: GoogleSignInUseCase,
    val requestGoogleSignInUseCase: RequestGoogleSignInUseCase,
    val loadTokensUseCase: LoadTokensUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    fun checkExistingCredentials(activity: Activity) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            googleSignInUseCase.invoke(activity)
                .onSuccess { credentials ->
                    if (credentials != null) {
                        loadTokensUseCase.invoke()
                        _authState.value = AuthState.Authenticated(credentials)
                    } else {
                        requestGoogleSignIn(activity)
                    }
                }
                .onFailure { error ->
                    _authState.value = AuthState.Error(error.message ?: "Erro desconhecido")
                }
        }
    }

    fun requestGoogleSignIn(activity: Activity) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            requestGoogleSignInUseCase.invoke(activity)
                .onSuccess { credentials ->
                    _authState.value = AuthState.Authenticated(credentials)
                }
                .onFailure { error ->
                    _authState.value = AuthState.Error(error.message ?: "Falha no login")
                }
        }
    }


}
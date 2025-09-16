package com.apptester.tv.presentation.features.authentication

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apptester.tv.domain.credentials.GoogleSignInUseCase
import com.apptester.tv.domain.credentials.GoogleSignOutUseCase
import com.apptester.tv.domain.credentials.RequestGoogleSignInUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class AuthViewModel(
    val googleSignInUseCase: GoogleSignInUseCase,
    val requestGoogleSignInUseCase: RequestGoogleSignInUseCase
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun checkExistingCredentials(activity: Activity) {
        viewModelScope.launch {
            _loading.value = true

            googleSignInUseCase.invoke(activity)
                .onSuccess { credentials ->
                    if (credentials != null) {
                        _authState.value = AuthState.Authenticated(credentials)
                    } else {
                        requestGoogleSignIn(activity)
                    }
                }
                .onFailure { error ->
                    _authState.value = AuthState.Error(error.message ?: "Erro desconhecido")
                }

            _loading.value = false
        }
    }

    fun requestGoogleSignIn(activity: Activity) {
        viewModelScope.launch {
            _loading.value = true

            requestGoogleSignInUseCase.invoke(activity)
                .onSuccess { credentials ->
                    _authState.value = AuthState.Authenticated(credentials)
                }
                .onFailure { error ->
                    _authState.value = AuthState.Error(error.message ?: "Falha no login")
                }

            _loading.value = false
        }
    }


}
package com.apptester.tv.presentation.ui.authentication

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.apptester.tv.R
import org.koin.androidx.compose.koinViewModel


@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel<AuthViewModel>(),
    onNavigateToAppSelection: () -> Unit
) {
    val activity = LocalActivity.current
    LaunchedEffect(Unit) {
        viewModel.checkExistingCredentials(activity!!)
    }

    val authState by viewModel.authState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        when (authState) {
            is AuthState.Loading -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_launcher),
                        contentDescription = "Launcher Icon"
                    )
                    CircularProgressIndicator()
                }

            }

            is AuthState.Error -> {
                val message = (authState as AuthState.Error).message
                Text(text = message)
            }

            is AuthState.Authenticated -> {
                onNavigateToAppSelection.invoke()
            }

            AuthState.Unauthenticated -> {

            }
        }

    }


}
package com.apptester.tv.presentation.ui.authentication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.apptester.tv.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class AuthActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModel<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setupUI()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        viewModel.checkExistingCredentials(this)
    }

//    private fun setupUI() {
//        viewModel.authState.observe(this) { state ->
//            when (state) {
//                is AuthState.Authenticated -> {
//                    // Usuário autenticado
//                    Log.d("AuthActivity", "Usuário autenticado")
//                }
//
//                is AuthState.Unauthenticated -> {
//                    Log.d("AuthActivity", "Usuário não autenticado")
//                    // Mostrar tela de login
//                }
//
//                is AuthState.Error -> {
//                    Log.e("AuthActivity", "Erro: ${state.message}")
//                    // Mostrar erro
//                }
//
//                AuthState.Loading -> TODO()
//            }
//        }
//    }
}

@Composable
fun AppBox(modifier: Modifier = Modifier) {

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(
    device = Devices.TV_1080p,
    showBackground = true
)
@Composable
fun GreetingPreview() {
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
package com.apptester.tv.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.apptester.tv.presentation.ui.appselection.AppSelectionScreen
import com.apptester.tv.presentation.ui.authentication.AuthenticationScreen
import com.apptester.tv.presentation.ui.splash.SplashScreen
import com.apptester.tv.theme.AppTheme

class MainActivity: ComponentActivity() {

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.White)
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }

    }

    @Composable
    fun NavGraph(
        navController: NavHostController,
        startDestination: String = Screen.Splash.route
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = Screen.Splash.route) {
                SplashScreen(
                    onNavigateToAuthentication = {
                        navController.navigate(Screen.Authentication.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(route = Screen.Authentication.route) {
                AuthenticationScreen(
                    onNavigateToAppSelection = {
                        navController.navigate(Screen.AppSelection.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(route = Screen.AppSelection.route) {
                AppSelectionScreen()
            }

            composable(route = Screen.VersionSelection.route) {
                AppSelectionScreen()
            }

        }
    }
}
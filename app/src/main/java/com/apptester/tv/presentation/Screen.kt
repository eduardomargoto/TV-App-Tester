package com.apptester.tv.presentation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Authentication : Screen("authentication")
    object AppSelection : Screen("app-selection")
    object VersionSelection : Screen("version-selection")
}
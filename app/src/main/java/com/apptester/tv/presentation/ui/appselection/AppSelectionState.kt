package com.apptester.tv.presentation.ui.appselection

import com.apptester.tv.data.entity.FirebaseApp

sealed class AppSelectionState {
    object Loading: AppSelectionState()
    data class Success(
        val apps: List<FirebaseApp>
    ): AppSelectionState()
    data class Error(
        val message: String
    ): AppSelectionState()
}
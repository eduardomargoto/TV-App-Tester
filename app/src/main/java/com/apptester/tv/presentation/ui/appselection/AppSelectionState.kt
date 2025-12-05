package com.apptester.tv.presentation.ui.appselection

sealed class AppSelectionState {
    object Loading: AppSelectionState()
    object Success: AppSelectionState()
    object Error: AppSelectionState()
}
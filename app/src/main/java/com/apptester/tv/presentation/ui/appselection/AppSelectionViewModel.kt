package com.apptester.tv.presentation.ui.appselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apptester.tv.domain.appdistribution.AppDistributionGetAppsUseCase
import com.apptester.tv.domain.appdistribution.AppDistributionGetProjectsUseCase
import com.apptester.tv.domain.appdistribution.AppDistributionGetReleasesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppSelectionViewModel(
    val appDistributionGetAppsUseCase: AppDistributionGetAppsUseCase,
    val appDistributionGetProjectsUseCase: AppDistributionGetProjectsUseCase,
    val appDistributionGetReleasesUseCase: AppDistributionGetReleasesUseCase,
): ViewModel() {

    private val _state = MutableStateFlow<AppSelectionState>(AppSelectionState.Loading)
    val state = _state.asStateFlow()

    fun getReleases(
        projectId: String,
        appId: String
    ) {
        viewModelScope.launch {
            appDistributionGetReleasesUseCase.invoke(projectId, appId)
                .onSuccess {
                    _state.value = AppSelectionState.Success
                }
                .onFailure {
                    _state.value = AppSelectionState.Error
                }
        }
    }

    fun getApps(
        projectId: String,
    ) {
        viewModelScope.launch {
            appDistributionGetAppsUseCase.invoke(projectId)
                .onSuccess {
                    _state.value = AppSelectionState.Success
                }
                .onFailure {
                    _state.value = AppSelectionState.Error
                }
        }
    }

    fun getProjects() {
        viewModelScope.launch {
            appDistributionGetProjectsUseCase.invoke()
                .onSuccess {
                    _state.value = AppSelectionState.Success
                }
                .onFailure {
                    _state.value = AppSelectionState.Error
                }
        }
    }





}
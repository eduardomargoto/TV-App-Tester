package com.apptester.tv.presentation.ui.appselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apptester.tv.data.entity.FirebaseApp
import com.apptester.tv.data.entity.FirebaseProject
import com.apptester.tv.domain.usecases.appdistribution.AppDistributionGetAppsUseCase
import com.apptester.tv.domain.usecases.appdistribution.AppDistributionGetProjectsUseCase
import com.apptester.tv.domain.usecases.appdistribution.AppDistributionGetReleasesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppSelectionViewModel(
    val appDistributionGetAppsUseCase: AppDistributionGetAppsUseCase,
    val appDistributionGetProjectsUseCase: AppDistributionGetProjectsUseCase,
    val appDistributionGetReleasesUseCase: AppDistributionGetReleasesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<AppSelectionState>(AppSelectionState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                getProjects().also { projects ->
                    val apps = projects
                        .map { project ->
                            async { getApps(project.projectId) }
                        }
                        .awaitAll()
                        .flatten()
                    _state.value = AppSelectionState.Success(apps)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = AppSelectionState.Error(
                    e.message.toString()
                )
            }

        }
    }


    fun getReleases(
        projectId: String,
        appId: String
    ) {
        viewModelScope.launch {
            appDistributionGetReleasesUseCase.invoke(projectId, appId)
                .onSuccess {
                    _state.value = AppSelectionState.Success(
                        apps = emptyList()
                    )
                }
                .onFailure {
                    _state.value = AppSelectionState.Error(
                        it.message.toString()
                    )
                }
        }
    }

    private suspend fun getApps(
        projectId: String,
    ): List<FirebaseApp> = appDistributionGetAppsUseCase.invoke(projectId)


    private suspend fun getProjects(): List<FirebaseProject> =
        appDistributionGetProjectsUseCase.invoke()


}
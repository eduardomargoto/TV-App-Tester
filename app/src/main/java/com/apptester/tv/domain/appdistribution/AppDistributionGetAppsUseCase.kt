package com.apptester.tv.domain.appdistribution

import com.apptester.tv.data.entity.FirebaseApp
import com.apptester.tv.domain.repository.AppDistributionRepository

class AppDistributionGetAppsUseCase(
    val appDistributionRepository: AppDistributionRepository
) {
    suspend fun invoke(
        projectId: String,
        pageSize: Int? = null,
        pageToken: String? = null,
        showDeleted: Boolean = false
    ): Result<List<FirebaseApp>> =
        appDistributionRepository.getApps(projectId, pageSize, pageToken, showDeleted)
}
package com.apptester.tv.domain.appdistribution

import com.apptester.tv.domain.repository.AppDistributionRepository

class AppDistributionGetProjectsUseCase(
    val appDistributionRepository: AppDistributionRepository
) {
    suspend fun invoke(
        pageSize: Int? = null,
        pageToken: String? = null,
        showDeleted: Boolean = false
    ) = appDistributionRepository.getProjects(pageSize, pageToken, showDeleted)
}
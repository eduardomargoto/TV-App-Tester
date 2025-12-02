package com.apptester.tv.domain.appdistribution

import com.apptester.tv.domain.repository.AppDistributionRepository

class AppDistributionGetReleasesUseCase(
    val appDistributionRepository: AppDistributionRepository
) {
    suspend fun invoke(
        projectNumber: String,
        appId: String,
        pageSize: Int? = null
    ) = appDistributionRepository.getReleases(projectNumber, appId, pageSize)
}
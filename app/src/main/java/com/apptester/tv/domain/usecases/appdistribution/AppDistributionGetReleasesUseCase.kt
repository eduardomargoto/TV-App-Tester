package com.apptester.tv.domain.usecases.appdistribution

import com.apptester.tv.data.entity.FirebaseReleaseResponse
import com.apptester.tv.domain.repository.AppDistributionRepository

class AppDistributionGetReleasesUseCase(
    val appDistributionRepository: AppDistributionRepository
) {
    suspend fun invoke(
        projectNumber: String,
        appId: String,
        pageSize: Int? = null
    ): Result<List<FirebaseReleaseResponse>> =
        appDistributionRepository.getReleases(projectNumber, appId, pageSize)
}
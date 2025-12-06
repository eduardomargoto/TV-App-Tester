package com.apptester.tv.domain.usecases.appdistribution

import com.apptester.tv.data.entity.FirebaseProject
import com.apptester.tv.domain.repository.AppDistributionRepository

class AppDistributionGetProjectsUseCase(
    val appDistributionRepository: AppDistributionRepository
) {
    suspend fun invoke(
        pageSize: Int? = null,
        pageToken: String? = null,
        showDeleted: Boolean = false
    ): List<FirebaseProject> =
        appDistributionRepository.getProjects(pageSize, pageToken, showDeleted)
            .getOrThrow()
}
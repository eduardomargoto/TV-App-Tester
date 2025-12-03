package com.apptester.tv.data.repository

import com.apptester.tv.data.entity.FirebaseAppsResponse
import com.apptester.tv.data.entity.FirebaseProjectResponse
import com.apptester.tv.data.entity.FirebaseReleasesResponse
import com.apptester.tv.data.network.api.ApiAppDistribution
import com.apptester.tv.data.network.api.ApiFirebase
import com.apptester.tv.domain.repository.AppDistributionRepository

class AppDistributionRepositoryImpl(
    private val api: ApiAppDistribution,
    private val apiFirebase: ApiFirebase
) : AppDistributionRepository {

    override suspend fun getProjects(
        pageSize: Int?,
        pageToken: String?,
        showDeleted: Boolean
    ): FirebaseProjectResponse = apiFirebase.getProjects(pageSize, pageToken, showDeleted)

    override suspend fun getApps(
        projectId: String,
        pageSize: Int?,
        pageToken: String?,
        showDeleted: Boolean
    ): FirebaseAppsResponse = api.getApps(projectId, pageSize, pageToken, showDeleted)

    override suspend fun getReleases(
        projectNumber: String,
        appId: String,
        pageSize: Int?
    ): FirebaseReleasesResponse = api.getReleases(projectNumber, appId, pageSize)
}
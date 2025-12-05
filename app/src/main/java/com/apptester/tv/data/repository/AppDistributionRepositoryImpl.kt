package com.apptester.tv.data.repository

import com.apptester.tv.data.entity.FirebaseApp
import com.apptester.tv.data.entity.FirebaseProject
import com.apptester.tv.data.entity.FirebaseReleaseResponse
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
    ): Result<List<FirebaseProject>> =
        try {
            val projects = apiFirebase.getProjects(pageSize, pageToken, showDeleted).projects
            Result.success(projects)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getApps(
        projectId: String,
        pageSize: Int?,
        pageToken: String?,
        showDeleted: Boolean
    ): Result<List<FirebaseApp>> =
        try {
            val apps = api.getApps(projectId, pageSize, pageToken, showDeleted).apps
            Result.success(apps)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getReleases(
        projectNumber: String,
        appId: String,
        pageSize: Int?
    ): Result< List<FirebaseReleaseResponse>> =
        try {
            val releases = api.getReleases(projectNumber, appId, pageSize).releases
            Result.success(releases)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
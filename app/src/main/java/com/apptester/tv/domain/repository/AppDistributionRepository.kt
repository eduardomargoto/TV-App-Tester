package com.apptester.tv.domain.repository

import com.apptester.tv.data.entity.FirebaseAppsResponse
import com.apptester.tv.data.entity.FirebaseProjectResponse
import com.apptester.tv.data.entity.FirebaseReleasesResponse

interface AppDistributionRepository {

    suspend fun getProjects(
        pageSize: Int? = null,
        pageToken: String? = null,
        showDeleted: Boolean = false
    ): FirebaseProjectResponse

    suspend fun getApps(
        projectId: String,
        pageSize: Int? = null,
        pageToken: String? = null,
        showDeleted: Boolean = false
    ): FirebaseAppsResponse

    suspend fun getReleases(
        projectNumber: String,
        appId: String,
        pageSize: Int? = null
    ): FirebaseReleasesResponse
}
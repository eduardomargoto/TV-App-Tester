package com.apptester.tv.domain.repository

import com.apptester.tv.data.entity.FirebaseApp
import com.apptester.tv.data.entity.FirebaseProject
import com.apptester.tv.data.entity.FirebaseReleaseResponse

interface AppDistributionRepository {

    suspend fun getProjects(
        pageSize: Int? = null,
        pageToken: String? = null,
        showDeleted: Boolean = false
    ): Result<List<FirebaseProject>>

    suspend fun getApps(
        projectId: String,
        pageSize: Int? = null,
        pageToken: String? = null,
        showDeleted: Boolean = false
    ): Result<List<FirebaseApp>>

    suspend fun getReleases(
        projectNumber: String,
        appId: String,
        pageSize: Int? = null
    ): Result< List<FirebaseReleaseResponse>>
}
package com.apptester.tv.data.network.api

import com.apptester.tv.data.entity.FirebaseAppsResponse
import com.apptester.tv.data.entity.FirebaseProjectResponse
import com.apptester.tv.data.entity.FirebaseReleasesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

class ApiFirebaseDistribution(
    val client: HttpClient
) {
    suspend fun getProjects(
        pageSize: Int? = null,
        pageToken: String? = null,
        showDeleted: Boolean = false
    ): FirebaseProjectResponse = client.get {
        url {
            path("v1beta/projects")
            parameter("pageSize", pageSize)
            parameter("pageToken", pageToken)
            parameter("showDeleted", showDeleted)
        }
    }.body()

    suspend fun getApps(
        projectId: String,
        pageSize: Int? = null,
        pageToken: String? = null,
        showDeleted: Boolean = false
    ): FirebaseAppsResponse = client.get {
        url {
            path("v1beta1/projects", projectId, "androidApps")
            parameter("pageSize", pageSize)
            parameter("pageToken", pageToken)
            parameter("showDeleted", showDeleted)
        }
    }.body()

    suspend fun getReleases(
        projectNumber: String,
        appId: String,
        pageSize: Int? = null
    ): FirebaseReleasesResponse = client.get {
        url {
            path("v1/projects", projectNumber, "apps", appId, "releases")
            parameter("pageSize", pageSize)
        }
    }.body()

}
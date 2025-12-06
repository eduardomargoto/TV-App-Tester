package com.apptester.tv.data.network.api

import com.apptester.tv.data.entity.FirebaseProjectResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

class ApiFirebase(
    val client: HttpClient
) {

    suspend fun getProjects(
        pageSize: Int? = null,
        pageToken: String? = null,
        showDeleted: Boolean = false
    ): FirebaseProjectResponse = client.get {
        url {
            path("v1beta1/projects")
            parameter("pageSize", pageSize)
            parameter("pageToken", pageToken)
            parameter("showDeleted", showDeleted)
        }
    }.body()


}
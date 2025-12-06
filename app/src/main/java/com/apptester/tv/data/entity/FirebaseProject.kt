package com.apptester.tv.data.entity

import kotlinx.serialization.SerialName

data class FirebaseProject(
    @SerialName("projectId") val projectId: String,
    @SerialName("projectNumber") val projectNumber: String,
    @SerialName("displayName") val displayName: String,
    @SerialName("name") val name: String,
    @SerialName("etag") val etag: String,
)
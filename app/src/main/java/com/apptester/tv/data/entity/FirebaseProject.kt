package com.apptester.tv.data.entity

import com.google.gson.annotations.SerializedName

data class FirebaseProject(
    @SerializedName("projectId") val projectId: String,
    @SerializedName("projectNumber") val projectNumber: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("name") val name: String,
    @SerializedName("etag") val etag: String,
)
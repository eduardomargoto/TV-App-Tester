package com.apptester.tv.data.entity

import com.google.gson.annotations.SerializedName

data class FirebaseReleaseResponse(
    @SerializedName("name") val name: String,
    @SerializedName("releaseNotes") val releaseNotes: FirebaseReleaseNotesResponse? = null,
    @SerializedName("displayVersion") val displayVersion: String? = null,
    @SerializedName("buildVersion") val buildVersion: String? = null,
    @SerializedName("createTime") val createTime: String? = null,
    @SerializedName("firebaseConsoleUri") val firebaseConsoleUri: String? = null,
    @SerializedName("testingUri") val testingUri: String? = null,
    @SerializedName("binaryDownloadUri") val binaryDownloadUri: String? = null
)
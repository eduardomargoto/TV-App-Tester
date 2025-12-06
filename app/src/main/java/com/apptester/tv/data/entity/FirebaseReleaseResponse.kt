package com.apptester.tv.data.entity

import kotlinx.serialization.SerialName

data class FirebaseReleaseResponse(
    @SerialName("name") val name: String,
    @SerialName("releaseNotes") val releaseNotes: FirebaseReleaseNotesResponse? = null,
    @SerialName("displayVersion") val displayVersion: String? = null,
    @SerialName("buildVersion") val buildVersion: String? = null,
    @SerialName("createTime") val createTime: String? = null,
    @SerialName("firebaseConsoleUri") val firebaseConsoleUri: String? = null,
    @SerialName("testingUri") val testingUri: String? = null,
    @SerialName("binaryDownloadUri") val binaryDownloadUri: String? = null
)
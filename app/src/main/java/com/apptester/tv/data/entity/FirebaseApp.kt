package com.apptester.tv.data.entity

import kotlinx.serialization.SerialName

data class FirebaseApp(
    @SerialName("appId") val appId: String,
    @SerialName("projectId") val projectId: String,
    @SerialName("name") val name: String,
    @SerialName("displayName") val displayName: String,
    @SerialName("packageName") val packageName: String,
    @SerialName("apiKeyId") val apiKeyId: String,
    @SerialName("expireTime") val expireTime: String,
    @SerialName("etag") val etag: String,
)
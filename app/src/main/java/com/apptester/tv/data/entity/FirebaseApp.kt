package com.apptester.tv.data.entity

import com.google.gson.annotations.SerializedName

data class FirebaseApp(
    @SerializedName("appId") val appId: String,
    @SerializedName("projectId") val projectId: String,
    @SerializedName("name") val name: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("packageName") val packageName: String,
    @SerializedName("apiKeyId") val apiKeyId: String,
    @SerializedName("expireTime") val expireTime: String,
    @SerializedName("etag") val etag: String,
)
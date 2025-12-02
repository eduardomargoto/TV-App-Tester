package com.apptester.tv.data.entity

import com.google.gson.annotations.SerializedName

data class FirebaseAppsResponse(
    @SerializedName("apps") val apps: List<FirebaseApp>
)
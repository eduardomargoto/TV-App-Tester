package com.apptester.tv.data.entity

import kotlinx.serialization.SerialName

data class FirebaseAppsResponse(
    @SerialName("apps") val apps: List<FirebaseApp>
)
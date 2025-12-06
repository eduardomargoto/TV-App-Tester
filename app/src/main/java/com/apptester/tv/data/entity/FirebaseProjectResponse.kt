package com.apptester.tv.data.entity

import kotlinx.serialization.SerialName

data class FirebaseProjectResponse(
    @SerialName("results") val projects: List<FirebaseProject>
)






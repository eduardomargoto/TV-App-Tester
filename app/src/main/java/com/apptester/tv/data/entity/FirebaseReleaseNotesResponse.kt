package com.apptester.tv.data.entity

import kotlinx.serialization.SerialName

data class FirebaseReleaseNotesResponse(
    @SerialName("text") val text: String
)
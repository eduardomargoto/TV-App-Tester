package com.apptester.tv.data.entity

import kotlinx.serialization.SerialName

data class FirebaseReleasesResponse (
    @SerialName("releases") val releases: List<FirebaseReleaseResponse>
)
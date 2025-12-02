package com.apptester.tv.data.entity

import com.google.gson.annotations.SerializedName

data class FirebaseReleasesResponse (
    @SerializedName("releases") val releases: List<FirebaseReleaseResponse>
)
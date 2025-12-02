package com.apptester.tv.data.entity

import com.google.gson.annotations.SerializedName

data class FirebaseReleaseNotesResponse(
    @SerializedName("text") val text: String
)
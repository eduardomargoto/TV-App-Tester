package com.apptester.tv.data.entity

import com.google.gson.annotations.SerializedName

data class FirebaseProjectResponse(
    @SerializedName("results") val projects: List<FirebaseProject>
)






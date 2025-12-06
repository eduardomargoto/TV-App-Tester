package com.apptester.tv.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
)
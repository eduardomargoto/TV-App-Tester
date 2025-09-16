package com.apptester.tv.domain.credentials

data class UserCredentials(
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val idToken: String
)
package com.apptester.tv.domain

data class UserCredentials(
    val email: String,
    val displayName: String?,
    val photoUrl: String?,
    val idToken: String
)
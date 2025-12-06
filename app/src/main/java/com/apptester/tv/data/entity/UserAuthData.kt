package com.apptester.tv.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthData(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val idToken: String,
    val tokenType: String,
    val scope: String,
    var code: String,
    val profile: UserProfile
) {
    fun isAccessTokenValid(): Boolean {
        return expiresIn > System.currentTimeMillis() / 1000
    }
}
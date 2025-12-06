package com.apptester.tv.data.network.api

import com.apptester.tv.BuildConfig
import com.apptester.tv.data.entity.AccessTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.http.path
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class ApiGoogleOAuth2(
    val client: HttpClient
) {

    suspend fun getToken(
        code: String,
        idToken: String
    ): AccessTokenResponse = client.get {
        url {
            path("token")
        }
        setBody(TokenRequestBody(authCode = code, idToken = idToken))
    }.body()

    suspend fun refreshToken(
        refreshToken: String
    ): AccessTokenResponse = client.get {
        url {
            path("token")
        }
        setBody(RefreshTokenRequestBody(refreshToken = refreshToken))
    }.body()


    @Serializable
    private data class TokenRequestBody(
        @SerialName("grant_type") val grantType: String = "authorization_code",
        @SerialName("client_id") val clientId: String = BuildConfig.SERVER_CLIENT_ID,
        @SerialName("client_secret") val clientSecret: String = BuildConfig.CLIENT_SECRET,
        @SerialName("redirect_uri") val redirectUri: String = "",
        @SerialName("code") val authCode: String,
        @SerialName("id_token") val idToken: String
    )

    @Serializable
    private data class RefreshTokenRequestBody(
        @SerialName("grant_type") val grantType: String = "refresh_token",
        @SerialName("client_id") val clientId: String = BuildConfig.SERVER_CLIENT_ID,
        @SerialName("client_secret") val clientSecret: String = BuildConfig.CLIENT_SECRET,
        @SerialName("refresh_token") val refreshToken: String = "",

        )
}
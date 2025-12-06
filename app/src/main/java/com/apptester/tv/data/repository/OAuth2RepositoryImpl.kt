package com.apptester.tv.data.repository

import com.apptester.tv.data.entity.AccessTokenResponse
import com.apptester.tv.data.local.AuthDataStore
import com.apptester.tv.data.network.api.ApiGoogleOAuth2
import com.apptester.tv.domain.repository.OAuth2Repository

class OAuth2RepositoryImpl(
    val apiGoogleOAuth2: ApiGoogleOAuth2,
    val authDataStore: AuthDataStore
): OAuth2Repository {
    override suspend fun loadTokens(): Result<AccessTokenResponse> {
        return try {
            authDataStore.getUserDataOnce()?.let {
                if (it.isAccessTokenValid()) {
                    Result.success(it)
                }
                val response = apiGoogleOAuth2.getToken(it.code, it.idToken)
                authDataStore.updateTokens(
                    response.accessToken,
                    response.refreshToken,
                    response.expiresIn
                )
                Result.success(response)
            } ?: run {
                Result.failure(IllegalArgumentException("User data not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun refreshToken(): Result<AccessTokenResponse> {
        return try {
            authDataStore.getUserDataOnce()?.let {
                val response = apiGoogleOAuth2.refreshToken(it.refreshToken)
                authDataStore.updateTokens(
                    response.accessToken,
                    response.refreshToken,
                    response.expiresIn
                )
                Result.success(response)
            } ?: run {
                Result.failure(IllegalArgumentException("User data not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
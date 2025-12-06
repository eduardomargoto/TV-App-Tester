package com.apptester.tv.domain.repository

import com.apptester.tv.data.entity.AccessTokenResponse

interface OAuth2Repository {

    suspend fun loadTokens(): Result<AccessTokenResponse>
    suspend fun refreshToken(): Result<AccessTokenResponse>
}
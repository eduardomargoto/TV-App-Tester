package com.apptester.tv.domain.usecases.authentication

import com.apptester.tv.data.entity.AccessTokenResponse
import com.apptester.tv.domain.repository.OAuth2Repository

class RefreshTokensUseCase(
    val oAuth2Repository: OAuth2Repository
) {
    suspend fun invoke(): AccessTokenResponse =
        oAuth2Repository.refreshToken().getOrThrow()
}
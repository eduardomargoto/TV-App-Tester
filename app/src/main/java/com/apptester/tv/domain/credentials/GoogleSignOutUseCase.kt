package com.apptester.tv.domain.credentials

import com.apptester.tv.data.network.CredentialsRepository

class GoogleSignOutUseCase(
    val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return credentialsRepository.clearCredentials()
    }
}
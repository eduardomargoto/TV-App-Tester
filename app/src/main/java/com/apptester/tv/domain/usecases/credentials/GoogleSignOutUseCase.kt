package com.apptester.tv.domain.usecases.credentials

import com.apptester.tv.domain.repository.CredentialsRepository

class GoogleSignOutUseCase(
    val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return credentialsRepository.clearCredentials()
    }
}
package com.apptester.tv.domain.credentials

import android.app.Activity
import com.apptester.tv.domain.UserCredentials
import com.apptester.tv.domain.repository.CredentialsRepository

class RequestGoogleSignInUseCase(
    val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(activityContext: Activity): Result<UserCredentials> {
        return credentialsRepository.requestGoogleCredentials(activityContext)
    }
}
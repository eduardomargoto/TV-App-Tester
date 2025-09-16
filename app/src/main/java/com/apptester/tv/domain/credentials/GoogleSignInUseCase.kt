package com.apptester.tv.domain.credentials

import android.app.Activity
import com.apptester.tv.data.network.CredentialsRepository

class GoogleSignInUseCase(
    val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(activityContext: Activity): Result<UserCredentials?> {
        return credentialsRepository.getCredentials(activityContext)
    }
}
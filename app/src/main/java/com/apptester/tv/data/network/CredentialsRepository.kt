package com.apptester.tv.data.network

import android.app.Activity
import com.apptester.tv.domain.credentials.UserCredentials

interface CredentialsRepository {

    suspend fun getCredentials(activityContext: Activity): Result<UserCredentials?>
    suspend fun clearCredentials(): Result<Unit>
    suspend fun requestGoogleCredentials(activityContext: Activity): Result<UserCredentials>
}
package com.apptester.tv.domain.repository

import android.app.Activity
import com.apptester.tv.domain.UserCredentials

interface CredentialsRepository {

    suspend fun getCredentials(activityContext: Activity): Result<UserCredentials?>
    suspend fun clearCredentials(): Result<Unit>
    suspend fun requestGoogleCredentials(activityContext: Activity): Result<UserCredentials>
}
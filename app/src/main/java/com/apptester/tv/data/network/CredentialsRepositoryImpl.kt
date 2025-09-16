package com.apptester.tv.data.network

import android.app.Activity
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.apptester.tv.BuildConfig
import com.apptester.tv.domain.credentials.UserCredentials
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class CredentialsRepositoryImpl(
    val credentialsManager: CredentialManager
) : CredentialsRepository {

    override suspend fun getCredentials(activityContext: Activity): Result<UserCredentials?> {
        return try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(BuildConfig.SERVER_CLIENT_ID)
                .setAutoSelectEnabled(true)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialsManager.getCredential(
                context = activityContext,
                request = request
            )

            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.data)

            val userCredentials = UserCredentials(
                email = googleIdTokenCredential.id,
                displayName = googleIdTokenCredential.displayName,
                photoUrl = googleIdTokenCredential.profilePictureUri?.toString(),
                idToken = googleIdTokenCredential.idToken
            )

            Result.success(userCredentials)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearCredentials(): Result<Unit> {
        return try {
            credentialsManager.clearCredentialState(
                ClearCredentialStateRequest()
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun requestGoogleCredentials(activityContext: Activity): Result<UserCredentials> =
        withContext(Dispatchers.IO) {
            try {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.SERVER_CLIENT_ID)
                    .setAutoSelectEnabled(true)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialsManager.getCredential(
                    request = request,
                    context = activityContext,
                )

                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)

                val userCredentials = UserCredentials(
                    email = googleIdTokenCredential.id,
                    displayName = googleIdTokenCredential.displayName,
                    photoUrl = googleIdTokenCredential.profilePictureUri?.toString(),
                    idToken = googleIdTokenCredential.idToken
                )

                Result.success(userCredentials)
            } catch (e: GetCredentialException) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
}
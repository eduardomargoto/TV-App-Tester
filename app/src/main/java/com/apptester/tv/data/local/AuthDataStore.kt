package com.apptester.tv.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.apptester.tv.data.entity.UserAuthData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.io.IOException


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_preferences")

class AuthDataStore(private val context: Context) {

    companion object {
        private val KEY_USER_AUTH_DATA = stringPreferencesKey("user_data_json")
        private val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

    suspend fun saveUserData(userAuthData: UserAuthData) {
        val jsonString = json.encodeToString(userAuthData)
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_AUTH_DATA] = jsonString
        }
    }

    suspend fun updateTokens(
        accessToken: String,
        refreshToken: String,
        expiresIn: Long
    ) {
        context.dataStore.edit { preferences ->
            val jsonString = preferences[KEY_USER_AUTH_DATA] ?: return@edit
            val currentData = runCatching {
                json.decodeFromString<UserAuthData>(jsonString)
            }.getOrNull() ?: return@edit

            val updatedData = currentData.copy(
                accessToken = accessToken,
                refreshToken = refreshToken,
                expiresIn = expiresIn
            )

            preferences[KEY_USER_AUTH_DATA] = json.encodeToString(updatedData)
        }
    }

    // Flow com o UserData completo (ou null se não existir / estiver inválido)
    val userAuthData: Flow<UserAuthData?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val jsonString = preferences[KEY_USER_AUTH_DATA] ?: return@map null
            runCatching {
                json.decodeFromString<UserAuthData>(jsonString)
            }.getOrNull()
        }



    val isAuthenticated: Flow<Boolean> = userAuthData.map { data ->
        !data?.accessToken.isNullOrBlank()
    }

    suspend fun getUserDataOnce(): UserAuthData? {
        return userAuthData.firstOrNull()
    }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}
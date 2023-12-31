package com.ingridentify.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ingridentify.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[NAME] = user.name
            preferences[EMAIL] = user.email
            preferences[TOKEN] = user.token
        }
    }

    fun getSession(): Flow<UserModel?> {
        return dataStore.data.map { preferences ->
            val name = preferences[NAME] ?: return@map null
            val email = preferences[EMAIL] ?: return@map null
            val token = preferences[TOKEN] ?: return@map null

            UserModel(name, email, token)
        }
    }

    suspend fun getToken(): String {
        val userModel = getSession().first() ?: return ""
        return "Bearer ${userModel.token}"
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
        private val TOKEN = stringPreferencesKey("token")

        @Volatile
        private var instance: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return instance ?: synchronized(this) {
                instance ?: UserPreference(dataStore).also { instance = it }
            }
        }
    }
}
package com.ingridentify.di

import android.content.Context
import com.ingridentify.data.Repository
import com.ingridentify.data.datastore.UserPreference
import com.ingridentify.data.datastore.userDataStore
import com.ingridentify.data.local.IngridentifyDatabase
import com.ingridentify.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = IngridentifyDatabase.getInstance(context)
        val apiService = ApiConfig.getApiService()
        val userPreference = UserPreference.getInstance(context.userDataStore)
        return Repository.getInstance(database, apiService, userPreference)
    }
}
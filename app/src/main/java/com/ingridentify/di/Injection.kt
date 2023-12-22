package com.ingridentify.di

import android.content.Context
import com.ingridentify.data.Repository
import com.ingridentify.data.datastore.UserPreference
import com.ingridentify.data.datastore.userDataStore
import com.ingridentify.data.local.IngridentifyDatabase
import com.ingridentify.data.remote.retrofit.ApiConfig
import com.ingridentify.data.remote.retrofit.ApiService
import com.ingridentify.data.remote.retrofit.MLService

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = IngridentifyDatabase.getInstance(context)
        val apiService: ApiService = ApiConfig.getApiService()
        val mlService: MLService = ApiConfig.getMlService()
        val userPreference = UserPreference.getInstance(context.userDataStore)
        return Repository.getInstance(database, apiService, mlService, userPreference)
    }
}
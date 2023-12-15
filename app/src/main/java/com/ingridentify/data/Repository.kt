package com.ingridentify.data

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.ingridentify.data.datastore.UserPreference
import com.ingridentify.data.model.UserModel
import com.ingridentify.data.remote.response.ErrorResponse
import com.ingridentify.data.remote.response.LoginResponse
import com.ingridentify.data.remote.retrofit.ApiService
import retrofit2.HttpException

class Repository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun checkSession() = userPreference.getSession().asLiveData()

    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val response: LoginResponse = apiService.login(email, password)
            val userModel = UserModel(
                username = response.name,
                name = response.username,
                email = response.email,
                token = response.token
            )
            userPreference.saveSession(userModel)
            emit(Result.Success(true))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            emit(Result.Error(errorBody.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun logout() {
        userPreference.clearSession()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, userPreference).apply { instance = this }
            }
    }
}
package com.ingridentify.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.ingridentify.data.datastore.UserPreference
import com.ingridentify.data.local.IngridentifyDatabase
import com.ingridentify.data.model.RecipeModel
import com.ingridentify.data.model.UserModel
import com.ingridentify.data.paging.HistoryRemoteMediator
import com.ingridentify.data.remote.response.ErrorResponse
import com.ingridentify.data.remote.response.LoginResponse
import com.ingridentify.data.remote.response.RegisterResponse
import com.ingridentify.data.remote.retrofit.ApiService
import retrofit2.HttpException

class Repository private constructor(
    private val database: IngridentifyDatabase,
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    private val historyDao = database.historyDao()
    private val recipeDao = database.recipeDao()

    fun checkSession(): LiveData<UserModel?> = userPreference.getSession().asLiveData()

    fun register(name: String, email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val response: RegisterResponse = apiService.register(name, email, password)
            if (response.data != null) {
                emit(Result.Success(response.message))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            emit(Result.Error(errorBody.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val response: LoginResponse = apiService.login(email, password)
            val userData = response.userData
            val userModel = UserModel(
                name = userData.name,
                email = userData.email,
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

    @OptIn(ExperimentalPagingApi::class)
    fun getHistories(): LiveData<PagingData<RecipeModel>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        remoteMediator = HistoryRemoteMediator(database, apiService, userPreference),
        pagingSourceFactory = { historyDao.getAll() }
    ).liveData

    fun getBookmarkedRecipe() : LiveData<PagingData<RecipeModel>> = Pager(
        config = PagingConfig(
            pageSize = 10
        ),
        pagingSourceFactory = { recipeDao.getAll() }
    ).liveData

    //FIXME: the recipe should not be nullable
    fun getRecipeDetail(id: Int): LiveData<RecipeModel?> = liveData {
        emit(recipeDao.getById(id) ?: historyDao.getById(id))
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(database: IngridentifyDatabase, apiService: ApiService, userPreference: UserPreference): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(database, apiService, userPreference).apply { instance = this }
            }
    }
}
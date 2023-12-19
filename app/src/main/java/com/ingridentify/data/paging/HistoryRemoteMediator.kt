package com.ingridentify.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ingridentify.data.datastore.UserPreference
import com.ingridentify.data.local.IngridentifyDatabase
import com.ingridentify.data.local.entity.HistoryEntity
import com.ingridentify.data.local.entity.RemoteKeys
import com.ingridentify.data.model.RecipeModel
import com.ingridentify.data.remote.retrofit.ApiService
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class HistoryRemoteMediator(
    private val database: IngridentifyDatabase,
    private val apiService: ApiService,
    private val userPreference: UserPreference
) : RemoteMediator<Int, RecipeModel>() {

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RecipeModel>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeyById(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RecipeModel>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeyById(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RecipeModel>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { recipeId ->
                database.remoteKeysDao().getRemoteKeyById(recipeId)
            }
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, RecipeModel>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)

                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                nextKey
            }
        }

        try {
            //TODO: get the actual data from the API
            val recipes = listOf<HistoryEntity>()
//            val recipes = apiService.getHistory(
//                token = userPreference.getToken(),
//                page = page,
//                limit = state.config.pageSize
//            ).map { it.toEntity() }


            val endOfPaginationReached = recipes.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.historyDao().deleteAll()
                    database.remoteKeysDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = recipes.map { recipe ->
                    RemoteKeys(id = recipe.id, prevKey = prevKey, nextKey = nextKey)
                }

                database.remoteKeysDao().insertAll(keys)
                database.historyDao().insertAll(recipes)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
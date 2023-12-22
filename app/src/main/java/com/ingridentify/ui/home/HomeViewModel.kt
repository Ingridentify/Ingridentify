package com.ingridentify.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ingridentify.data.Repository
import com.ingridentify.data.model.RecipeModel
import com.ingridentify.data.model.UserModel

class HomeViewModel(private val repository: Repository) : ViewModel() {
    val histories: LiveData<PagingData<RecipeModel>> = repository.getRecipes(bookmarked = true).cachedIn(viewModelScope)

    fun checkSession(): LiveData<UserModel?> = repository.checkSession()
}
package com.ingridentify.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingridentify.data.Repository
import com.ingridentify.data.Result
import com.ingridentify.data.model.RecipeModel
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {
    
    private val _result: MutableLiveData<Result<RecipeModel>> = MutableLiveData(Result.Loading)
    private val result: LiveData<Result<RecipeModel>> = _result
    private val _isBookmarked: MutableLiveData<Boolean> = MutableLiveData(false)
    val isBookmarked: LiveData<Boolean> = _isBookmarked

    fun getDetail(id: String): LiveData<Result<RecipeModel>> {
        _result.value = Result.Loading
        viewModelScope.launch {
            _result.value = repository.getRecipeDetail(id)
            _isBookmarked.value = repository.isBookmarked(id)
        }

        return result
    }

    fun toggleBookmark(id: String) {
        viewModelScope.launch {
            val isBookmarked = isBookmarked.value ?: false
            _isBookmarked.value = repository.setBookmark(id, !isBookmarked)
        }
    }
}
package com.ingridentify.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingridentify.data.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun checkSession() = repository.checkSession()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
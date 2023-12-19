package com.ingridentify.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ingridentify.data.Repository
import com.ingridentify.data.model.UserModel

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    fun getUser(): LiveData<UserModel?> = repository.checkSession()
}
package com.ingridentify.ui.login

import androidx.lifecycle.ViewModel
import com.ingridentify.data.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}
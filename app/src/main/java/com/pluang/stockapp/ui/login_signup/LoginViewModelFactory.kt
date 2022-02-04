package com.pluang.stockapp.ui.login_signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pluang.stockapp.data.AuthRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(
                loginRepository = AuthRepository(
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
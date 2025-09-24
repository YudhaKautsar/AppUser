package com.yudha.listuser.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yudha.listuser.data.model.User
import com.yudha.listuser.data.repository.UserRepository
import com.yudha.listuser.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : BaseViewModel(application) {
    private val repository = UserRepository(application.applicationContext)
    
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    
    fun fetchUsers() {
        viewModelScope.launch {
            setLoading(true)
            clearError()
            
            repository.getUsers()
                .onSuccess { userList ->
                    _users.value = userList
                }
                .onFailure { exception ->
                    setError(exception.message ?: "Terjadi kesalahan yang tidak diketahui")
                }
            
            setLoading(false)
        }
    }
}
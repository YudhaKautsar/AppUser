package com.yudha.listuser.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    
    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    protected val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
    
    protected fun setError(message: String) {
        _errorMessage.value = message
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}
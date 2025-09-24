package com.yudha.listuser.data.repository

import android.content.Context
import com.google.gson.JsonSyntaxException
import com.yudha.listuser.data.model.User
import com.yudha.listuser.network.ApiClient
import com.yudha.listuser.network.UserApiService
import com.yudha.listuser.util.ErrorType
import com.yudha.listuser.util.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserRepository(private val context: Context) {
    private val apiService = ApiClient.createService(UserApiService::class.java)

    suspend fun getUsers(): Result<List<User>> {
        return withContext(Dispatchers.IO) {
            try {
                // Check internet connection first
                if (!NetworkUtils.isNetworkAvailable(context)) {
                    return@withContext Result.failure(Exception(ErrorType.NoInternet.message))
                }
                
                val response = apiService.getUsers()
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        Result.success(users)
                    } else {
                        Result.failure(Exception(ErrorType.ParseError.message))
                    }
                } else {
                    val errorMessage = when (response.code()) {
                        in 400..499 -> "Kesalahan permintaan (${response.code()})"
                        in 500..599 -> ErrorType.ServerError.message
                        else -> "Kesalahan HTTP: ${response.code()}"
                    }
                    Result.failure(Exception(errorMessage))
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is UnknownHostException -> ErrorType.NoInternet.message
                    is SocketTimeoutException -> ErrorType.TimeoutError.message
                    is IOException -> ErrorType.NoInternet.message
                    is JsonSyntaxException -> ErrorType.ParseError.message
                    else -> ErrorType.UnknownError.message
                }
                Result.failure(Exception(errorMessage))
            }
        }
    }
}
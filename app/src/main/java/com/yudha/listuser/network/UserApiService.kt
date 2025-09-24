package com.yudha.listuser.network

import com.yudha.listuser.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}
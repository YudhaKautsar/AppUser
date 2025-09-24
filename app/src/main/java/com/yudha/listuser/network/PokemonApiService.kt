package com.yudha.listuser.network

import com.yudha.listuser.data.model.PokemonListResponse
import com.yudha.listuser.data.model.PokemonDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonListResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): Response<PokemonDetail>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetailByName(
        @Path("name") name: String
    ): Response<PokemonDetail>
}
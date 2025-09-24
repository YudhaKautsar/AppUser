package com.yudha.listuser.network

import com.yudha.listuser.network.ApiClient
import kotlin.LazyThreadSafetyMode.SYNCHRONIZED

/**
 * Container untuk semua layanan API. Menggunakan lazy initialization.
 */
object AppApi {
    val pokemon: PokemonApiService by lazy(SYNCHRONIZED) {
        ApiClient.createService(PokemonApiService::class.java)
    }

    // Tambahkan layanan lain di sini jika ada
    // val user: UserApiService by lazy(SYNCHRONIZED) { ... }
}
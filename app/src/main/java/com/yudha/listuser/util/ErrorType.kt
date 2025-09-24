package com.yudha.listuser.util

sealed class ErrorType(val message: String) {
    object NoInternet : ErrorType("Tidak ada koneksi internet. Periksa koneksi Anda dan coba lagi.")
    object ServerError : ErrorType("Terjadi kesalahan pada server. Silakan coba lagi nanti.")
    object TimeoutError : ErrorType("Koneksi timeout. Periksa koneksi internet Anda.")
    object ParseError : ErrorType("Terjadi kesalahan saat memproses data.")
    object UnknownError : ErrorType("Terjadi kesalahan yang tidak diketahui.")
    data class CustomError(val customMessage: String) : ErrorType(customMessage)
}
package com.blaze.moviesapp.other

sealed class AuthResult<out T> {
    data class Success<T>(val data: T): AuthResult<T>()
    data class Error(val message: String): AuthResult<Nothing>()
}

package com.example.taipeizoo.network

import java.io.IOException

sealed class NetworkResponse<out T : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T, val code: Int) : NetworkResponse<T>()

    /**
     * Failure response with Msg
     */
    data class ApiError(val errorMsg: String, val code: Int) : NetworkResponse<Nothing>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing>()
}
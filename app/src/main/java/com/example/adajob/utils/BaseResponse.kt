package com.example.adajob.utils

sealed class BaseResponses<out T> {
    data class Success<out T>(val data: T? = null) : BaseResponses<T>()
    data class Loading(val nothing: Nothing?=null) : BaseResponses<Nothing>()
    data class Error(val msg: String?) : BaseResponses<Nothing>()
}
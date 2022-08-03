package com.picpay.desafio.android.utils

import com.blankj.utilcode.util.StringUtils
import com.picpay.desafio.android.R

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T? = null) : Result<T>()
    data class Error<out T : Any>(val exception: Exception, val data: T? = null) : Result<T>()
    object InProgress : Result<Nothing>()
    object None : Result<Nothing>()
}

private const val NOT_AUTHORIZED: Int = 401
private const val FORBIDDEN: Int = 403
private const val NOT_FOUND: Int = 404

fun Int.codeResponseToString(): String {
    return when (this) {
        NOT_AUTHORIZED -> StringUtils.getString(R.string.information_not_authorized)
        FORBIDDEN -> StringUtils.getString(R.string.information_not_permission)
        NOT_FOUND -> StringUtils.getString(R.string.information_not_registered)
        else -> StringUtils.getString(R.string.generic_error_message)
    }
}

fun responseError(code: Int) =
    Result.Error(
        Exception(
            code.codeResponseToString()
        ), null
    )
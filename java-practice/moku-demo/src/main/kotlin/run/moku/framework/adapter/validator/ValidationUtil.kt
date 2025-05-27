package run.moku.framework.adapter.validator

import run.moku.framework.api.exception.ApiException
import run.moku.framework.api.response.ApiResponseCode

fun throwIf(condition: Boolean, errorCode: ApiResponseCode) {
    if (condition) throw ApiException(errorCode)
}

fun throwIf(condition: Boolean, exception: Throwable) {
    if (condition) throw exception
}

fun throwIfNot(condition: Boolean, errorCode: ApiResponseCode) {
    if (!condition) throw ApiException(errorCode)
}

fun throwIfNot(condition: Boolean, exception: Throwable) {
    if (!condition) throw exception
}
package com.sb.application.exception

fun applicationRequire(condition: Boolean, errorCode: ApplicationErrorCode) {
    if (!condition) throw ApplicationValidationException(errorCode)
}

inline fun applicationRequire(condition: Boolean, errorCode: ApplicationErrorCode, lazyMessage: () -> String) {
    if (!condition) throw ApplicationValidationException(errorCode, lazyMessage())
}

fun applicationFail(errorCode: ApplicationErrorCode): Nothing =
    throw ApplicationValidationException(errorCode)

inline fun applicationFail(errorCode: ApplicationErrorCode, lazyMessage: () -> String): Nothing =
    throw ApplicationValidationException(errorCode, lazyMessage())
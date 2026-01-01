package com.sb.application.exception

interface ApplicationErrorCode {
    val code: String
    val message: String
}

sealed class ApplicationException(
    val errorCode: ApplicationErrorCode,
    message: String = errorCode.message,
    cause: Throwable? = null,
) : RuntimeException(message, cause)

class ApplicationValidationException(
    errorCode: ApplicationErrorCode,
    message: String = errorCode.message,
    cause: Throwable? = null,
) : ApplicationException(errorCode, message, cause)
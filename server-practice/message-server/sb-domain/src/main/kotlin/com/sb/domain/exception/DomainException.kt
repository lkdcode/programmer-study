package com.sb.domain.exception

interface DomainErrorCode {
    val code: String
    val message: String
}

sealed class DomainException(
    val errorCode: DomainErrorCode,
    message: String = errorCode.message,
    cause: Throwable? = null,
) : RuntimeException(message, cause)

class DomainValidationException(
    errorCode: DomainErrorCode,
    message: String = errorCode.message,
    cause: Throwable? = null,
) : DomainException(errorCode, message, cause)
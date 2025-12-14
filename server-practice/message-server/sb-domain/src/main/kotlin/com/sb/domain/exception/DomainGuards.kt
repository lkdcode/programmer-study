package com.sb.domain.exception

fun domainRequire(condition: Boolean, errorCode: DomainErrorCode) {
    if (!condition) throw DomainValidationException(errorCode)
}

inline fun domainRequire(condition: Boolean, errorCode: DomainErrorCode, lazyMessage: () -> String) {
    if (!condition) throw DomainValidationException(errorCode, lazyMessage())
}

fun domainFail(errorCode: DomainErrorCode): Nothing =
    throw DomainValidationException(errorCode)

inline fun domainFail(errorCode: DomainErrorCode, lazyMessage: () -> String): Nothing =
    throw DomainValidationException(errorCode, lazyMessage())
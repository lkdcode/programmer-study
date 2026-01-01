package com.sb.application.common.validator

import com.sb.domain.exception.DomainErrorCode
import com.sb.domain.exception.DomainValidationException
import reactor.core.publisher.Mono


fun throwIf(condition: Boolean?, exception: Throwable) =
    if (condition != null && condition) throw exception else Unit

fun throwUnless(condition: Boolean, exception: Throwable) =
    if (!condition) throw exception else Unit

fun throwIf(condition: Boolean?, domainErrorCode: DomainErrorCode) =
    if (condition != null && condition) throw DomainValidationException(domainErrorCode) else Unit

fun throwUnless(condition: Boolean, domainErrorCode: DomainErrorCode) =
    if (!condition) throw DomainValidationException(domainErrorCode) else Unit

fun monoErrorIf(condition: Boolean, domainErrorCode: DomainErrorCode): Mono<Unit> =
    if (condition) Mono.error(DomainValidationException(domainErrorCode)) else Mono.empty()

fun monoErrorUnless(condition: Boolean, domainErrorCode: DomainErrorCode): Mono<Unit> =
    if (!condition) Mono.error(DomainValidationException(domainErrorCode)) else Mono.empty()
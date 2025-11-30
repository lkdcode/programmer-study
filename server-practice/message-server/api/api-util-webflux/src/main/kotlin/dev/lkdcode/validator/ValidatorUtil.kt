package dev.lkdcode.validator

import dev.lkdcode.api.exception.ApiException
import dev.lkdcode.api.response.ApiResponseCode
import reactor.core.publisher.Mono


fun throwIf(condition: Boolean, exception: Throwable): Mono<Void> =
    if (condition) Mono.error(exception) else Mono.empty()

fun throwIf(condition: Boolean, apiResponseCode: ApiResponseCode): Mono<Void> =
    if (condition) Mono.error(ApiException(apiResponseCode)) else Mono.empty()

fun throwUnless(condition: Boolean, exception: Throwable): Mono<Void> =
    if (!condition) Mono.error(exception) else Mono.empty()

fun throwUnless(condition: Boolean, apiResponseCode: ApiResponseCode): Mono<Void> =
    if (!condition) Mono.error(ApiException(apiResponseCode)) else Mono.empty()
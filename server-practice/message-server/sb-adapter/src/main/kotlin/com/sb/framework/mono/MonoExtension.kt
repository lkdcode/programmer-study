package com.sb.framework.mono

import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Mono

inline fun <T> monoSuspend(crossinline block: suspend () -> T): Mono<T> =
    Mono.defer { mono { block() } }
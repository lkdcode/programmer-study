package com.sb.framework.mono

import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Mono

inline fun monoSuspend(crossinline block: suspend () -> Unit): Mono<Void> =
    Mono.defer { mono { block() }.then() }

inline fun <T> monoSuspend(crossinline block: suspend () -> T): Mono<T> =
    Mono.defer { mono { block() } }
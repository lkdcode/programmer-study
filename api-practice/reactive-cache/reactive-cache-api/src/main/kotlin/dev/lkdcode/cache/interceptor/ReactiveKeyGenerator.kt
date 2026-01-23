package dev.lkdcode.cache.interceptor

import java.lang.reflect.Method

interface ReactiveKeyGenerator {
    fun generate(target: Any, method: Method, vararg params: Any): String
}
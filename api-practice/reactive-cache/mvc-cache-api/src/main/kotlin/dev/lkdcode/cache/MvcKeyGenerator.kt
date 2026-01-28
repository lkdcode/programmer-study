package dev.lkdcode.cache

import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component
class MvcKeyGenerator : KeyGenerator {

    override fun generate(target: Any, method: Method, vararg params: Any?): Any {
        val clazzName = target::class.java.name
        val methodName = method.name
        val paramName = params[0]?.toString()

        return "$paramName : $methodName - $clazzName"
    }
}
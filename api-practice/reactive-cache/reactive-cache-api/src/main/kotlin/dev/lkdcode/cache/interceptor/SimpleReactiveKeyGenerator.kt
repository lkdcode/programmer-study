package dev.lkdcode.cache.interceptor

import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component("simpleKeyGenerator")
class SimpleReactiveKeyGenerator : ReactiveKeyGenerator {

    override fun generate(target: Any, method: Method, vararg params: Any): String {
        val className = target.javaClass.simpleName
        val methodName = method.name
        val paramName = params[0].toString()

        return "$className:$methodName:$paramName"
    }
}
package dev.lkdcode.cache.interceptor

import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component("defaultKeyGenerator")
class DefaultReactiveKeyGenerator : ReactiveKeyGenerator {

    override fun generate(target: Any, method: Method, vararg params: Any): String {
        val className = target.javaClass.simpleName
        val methodName = method.name

        val paramsKey = if (params.isEmpty()) {
            "no-args"
        } else {
            params.joinToString(":") { param ->
                when (param) {
                    is Array<*> -> param.contentDeepToString()
                    is Collection<*> -> param.joinToString(",")
                    else -> param.toString()
                }
            }
        }

        return "$className:$methodName:$paramsKey"
    }
}

package dev.lkdcode.cache.interceptor

import dev.lkdcode.cache.parser.ReactiveCacheArgsParser
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component("simpleReactiveKeyGenerator")
class SimpleReactiveKeyGenerator(
    private val reactiveCacheArgsParser: ReactiveCacheArgsParser,
) : ReactiveKeyGenerator {

    override fun generate(
        target: Any,
        method: Method,
        key: String,
        args: Array<Any?>,
        paramNames: Array<String>
    ): String {
        val className = target.javaClass.simpleName
        val methodName = method.name
        val keyValue = reactiveCacheArgsParser.keyParse(key, args, paramNames)
        requireNotNull(keyValue) {
            "[SimpleReactiveKeyGenerator] 캐시 키가 null입니다. SpEL 표현식을 확인하세요. " +
            "메서드: $className::$methodName, key 표현식: $key"
        }

        return "$className::$methodName::$keyValue"
    }
}
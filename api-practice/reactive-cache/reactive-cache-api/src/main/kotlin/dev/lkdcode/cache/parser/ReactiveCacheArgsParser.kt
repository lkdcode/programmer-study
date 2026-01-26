package dev.lkdcode.cache.parser

import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component
import kotlin.apply
import kotlin.collections.forEachIndexed
import kotlin.jvm.java

@Component
class ReactiveCacheArgsParser {
    private val parser = SpelExpressionParser()

    fun keyParse(key: String, args: Array<Any?>, paramNames: Array<String>): String? {
        val context = StandardEvaluationContext().apply {
            paramNames.forEachIndexed { index, name ->
                setVariable(name, args[index])
            }
        }

        return parser
            .parseExpression(key)
            .getValue(context, String::class.java)
    }
}
package dev.lkdcode.cache.handler

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.expression.MethodBasedEvaluationContext
import org.springframework.core.DefaultParameterNameDiscoverer
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Component

@Component
class ReactiveCacheConditionHandler {
    private val parser = SpelExpressionParser()

    fun shouldCache(joinPoint: ProceedingJoinPoint, condition: String): Boolean {
        if (condition.isBlank()) return true

        val signature = joinPoint.signature as MethodSignature
        val context = MethodBasedEvaluationContext(
            joinPoint.target,
            signature.method,
            joinPoint.args,
            DefaultParameterNameDiscoverer()
        )

        return parser
            .parseExpression(condition)
            .getValue(context, Boolean::class.java) == true
    }
}
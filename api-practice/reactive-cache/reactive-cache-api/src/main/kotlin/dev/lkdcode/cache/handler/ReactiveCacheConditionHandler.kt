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
    private val parameterNameDiscoverer = DefaultParameterNameDiscoverer()

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

    fun shouldNotCache(joinPoint: ProceedingJoinPoint, condition: String): Boolean = !shouldCache(joinPoint, condition)

    fun shouldCacheResult(result: Any?, unless: String, joinPoint: ProceedingJoinPoint): Boolean {
        if (unless.isBlank()) return true
        if (result == null) return false

        val signature = joinPoint.signature as MethodSignature
        val context = MethodBasedEvaluationContext(
            joinPoint.target,
            signature.method,
            joinPoint.args,
            parameterNameDiscoverer
        )
        context.setVariable("result", result)

        return parser.parseExpression(unless).getValue(context, Boolean::class.java) != true
    }
}
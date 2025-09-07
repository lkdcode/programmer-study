package demo.example.query.modules.fruit.application.usecase.model

import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

abstract class BaseQueryString {
    fun convert(): ParamConditionList = ParamConditionList(
        this::class.memberProperties
            .filterIsInstance<KProperty1<Any, *>>()
            .mapNotNull { property ->
                val value = property.get(this) as? String
                if (!value.isNullOrBlank()) {
                    ParamCondition(property.name, value)
                } else null
            }
            .toList()
    )
}
package demo.example.query.modules.fruit.application.usecase.model

import java.time.LocalDate
import java.time.LocalDateTime


data class ParamConditionList(
    val list: List<ParamCondition>
) {
    fun add(paramCondition: ParamCondition): ParamConditionList =
        ParamConditionList(list + paramCondition)
}

data class ParamCondition(
    private val rawKey: String,
    private val rawValue: String,
) {
    val key get() = rawKey.trim()
    val valueString get() = rawValue.trim()
    val valueToBigDecimal get() = valueString.toBigDecimal()
    val valueToLong get() = valueString.toLong()
    val valueToInt get() = valueString.toInt()
    val valueToBoolean get() = valueString.toBoolean()
    val valueToDate get() = LocalDate.parse(valueString)
    val valueToDateTime get() = LocalDateTime.parse(valueString)
    fun isNotValue(): Boolean = key.isNotEmpty() && valueString.isNotEmpty()
}
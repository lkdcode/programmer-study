package run.moku.framework.jpa

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.Validator
import run.moku.framework.adapter.validator.throwIfNot

interface Validatable

private val validator: Validator by lazy {
    Validation.buildDefaultValidatorFactory().validator
}

fun <T : Validatable> T.validateSelf() {
    val violation = validator.validate(this)

    throwIfNot(violation.isEmpty(), ConstraintViolationException(violation))
}
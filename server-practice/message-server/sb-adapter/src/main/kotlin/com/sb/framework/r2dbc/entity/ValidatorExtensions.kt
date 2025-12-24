package com.sb.framework.r2dbc.entity

import com.sb.application.common.validator.throwIf
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.Validator

private val validator: Validator by lazy {
    Validation.buildDefaultValidatorFactory().validator
}

fun <T : Validatable> T.validate() {
    val violations = validator.validate(this)

    throwIf(violations.isNotEmpty(), ConstraintViolationException(violations))
}
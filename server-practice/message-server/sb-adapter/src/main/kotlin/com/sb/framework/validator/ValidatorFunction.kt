package com.sb.framework.validator


fun throwIf(condition: Boolean, exception: Throwable) =
    if (condition) exception else Unit

fun throwUnless(condition: Boolean, exception: Throwable) =
    if (!condition) exception else Unit
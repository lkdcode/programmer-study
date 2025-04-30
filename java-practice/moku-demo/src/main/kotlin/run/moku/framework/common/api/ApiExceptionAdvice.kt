package run.moku.framework.common.api

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import run.moku.framework.common.exception.MokuException

@RestControllerAdvice
class ApiExceptionAdvice {
    @ExceptionHandler(MokuException::class)
    fun mokuExceptionHandler() {
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionHandler(ex: IllegalArgumentException) {
        println("${ex.message}")
    }
}
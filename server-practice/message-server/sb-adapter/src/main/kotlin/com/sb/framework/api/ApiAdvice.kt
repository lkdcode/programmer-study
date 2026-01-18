package com.sb.framework.api

import com.sb.framework.log.logInfo
import com.sb.framework.log.logWarn
import io.r2dbc.spi.R2dbcDataIntegrityViolationException
import io.r2dbc.spi.R2dbcNonTransientResourceException
import io.r2dbc.spi.R2dbcRollbackException
import io.r2dbc.spi.R2dbcTransientResourceException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.resource.NoResourceFoundException
import org.springframework.web.server.MethodNotAllowedException
import org.springframework.web.server.ServerWebInputException
import java.sql.SQLException


@RestControllerAdvice
class ApiAdvice {

    @ExceptionHandler(ApiException::class)
    suspend fun handleApiException(e: ApiException): ApiResponseEntity<Any> =
        clientError(e.getApiResponseCode(), e.payload).also {
            logInfo("ApiResponseCode: ${e.getApiResponseCode()}, Message: ${e.message()} Status: ${e.status()}")
        }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    suspend fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ApiResponseEntity<List<*>> {
        val response = e.bindingResult.fieldErrors.map {
            logInfo("Field: ${it.field}, Rejected value: ${it.rejectedValue}, Message: ${it.defaultMessage}")

            mapOf(
                "Field" to it.field,
                "Rejected value" to it.rejectedValue,
                "Message" to it.defaultMessage
            )
        }

        return clientError(ApiResponseCode.REQUEST_INVALID, response)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    suspend fun handleConstraintViolationException(e: ConstraintViolationException): ApiResponseEntity<List<*>> {
        val response = e.constraintViolations.map {
            logInfo("Path: ${it.rootBeanClass}, Property: ${it.propertyPath} Invalid value: ${it.invalidValue}, Message: ${it.message}")

            mapOf(
                "Invalid value" to it.propertyPath.toString(),
                "Message" to it.message
            )
        }

        return clientError(ApiResponseCode.REQUEST_INVALID_DATA, response)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    suspend fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ApiResponseEntity<String> =
        clientError(
            apiResponseCode = ApiResponseCode.REQUEST_INVALID_BODY,
            payload = "${e.message}",
        ).also {
            logInfo("HttpMessageNotReadableException ${e.message}")
        }

    @ExceptionHandler(ServerWebInputException::class)
    suspend fun handleServerWebInputException(e: ServerWebInputException): ApiResponseEntity<String> =
        clientError(
            apiResponseCode = ApiResponseCode.REQUEST_INVALID_BODY,
            payload = e.message,
        ).also {
            logInfo("ServerWebInputException ${e.message}")
        }

    @ExceptionHandler(WebExchangeBindException::class)
    suspend fun handleWebExchangeBindException(
        e: WebExchangeBindException,
        request: ServerHttpRequest,
    ): ApiResponseEntity<String> {
        val errorMessage = e.allErrors
            .joinToString("; ") { it.defaultMessage ?: "Invalid request" }

        return clientError(
            apiResponseCode = ApiResponseCode.REQUEST_INVALID_BODY,
            payload = errorMessage
        ).also {
            logInfo("BindException - URI: ${request.uri.path} | Errors: $errorMessage")
        }
    }

    @ExceptionHandler(MethodNotAllowedException::class)
    suspend fun handleMethodNotAllowedException(
        e: MethodNotAllowedException,
        request: ServerHttpRequest
    ): ApiResponseEntity<String> =
        clientError(
            apiResponseCode = ApiResponseCode.REQUEST_UNSUPPORTED_METHOD,
            payload = "${e.httpMethod} ${request.uri.path} ${e.message}"
        ).also {
            logInfo("MethodNotAllowed - Method: ${e.httpMethod} | URI: ${request.uri.path}")
        }

    @ExceptionHandler(NoResourceFoundException::class)
    suspend fun handleNoResourceFoundException(e: NoResourceFoundException): ApiResponseEntity<String> =
        clientError(
            apiResponseCode = ApiResponseCode.REQUEST_UNSUPPORTED_REQUEST,
            payload = "API: /${e.message}, Message: ${e.message}",
        ).also {
            logInfo("End-Point: ${e.message} Message: ${e.message}")
        }

    @ExceptionHandler(SQLException::class)
    suspend fun handleSQLException(e: SQLException): ApiResponseEntity<Void> =
        serverError<Void>(
            apiResponseCode = ApiResponseCode.SEVER_SQL_EXCEPTION,
            payload = null,
        ).also {
            logWarn("SQLException Message: ${e.message}")
        }

    @ExceptionHandler(R2dbcDataIntegrityViolationException::class)
    suspend fun handleR2dbcDataIntegrityViolationException(e: R2dbcDataIntegrityViolationException): ApiResponseEntity<Void> =
        serverError<Void>(
            apiResponseCode = ApiResponseCode.SEVER_DATA_INTEGRITY_VIOLATION_EXCEPTION,
            payload = null,
        ).also {
            logWarn("R2dbcDataIntegrityViolationException Message: ${e.message}")
        }

    @ExceptionHandler(R2dbcTransientResourceException::class)
    suspend fun handleR2dbcTransientResourceException(e: R2dbcTransientResourceException): ApiResponseEntity<Void> =
        serverError<Void>(
            apiResponseCode = ApiResponseCode.SEVER_TRANSIENT_RESOURCE_EXCEPTION,
            payload = null,
        ).also {
            logWarn("R2dbcTransientResourceException Message: ${e.message}")
        }

    @ExceptionHandler(R2dbcNonTransientResourceException::class)
    suspend fun handleR2dbcNonTransientResourceException(e: R2dbcNonTransientResourceException): ApiResponseEntity<Void> =
        serverError<Void>(
            apiResponseCode = ApiResponseCode.SEVER_NON_TRANSIENT_RESOURCE_EXCEPTION,
            payload = null,
        ).also {
            logWarn("R2dbcNonTransientResourceException Message: ${e.message}")
        }

    @ExceptionHandler(R2dbcRollbackException::class)
    suspend fun handleR2dbcRollbackException(e: R2dbcRollbackException): ApiResponseEntity<Void> =
        serverError<Void>(
            apiResponseCode = ApiResponseCode.SEVER_ROLL_BACK_EXCEPTION,
            payload = null,
        ).also {
            logWarn("R2dbcRollbackException Message: ${e.message}")
        }

    @ExceptionHandler(RuntimeException::class)
    suspend fun handleRuntimeException(e: RuntimeException): ApiResponseEntity<Void> =
        serverError<Void>(
            apiResponseCode = ApiResponseCode.SEVER_UNHANDLED_EXCEPTION,
            payload = null,
        ).also {
            logWarn("[Runtime Exception] ${e::class.simpleName}: ${e.message}", e)
        }

    @ExceptionHandler(Exception::class)
    suspend fun handleException(e: Exception): ApiResponseEntity<Void> =
        serverError<Void>(
            apiResponseCode = ApiResponseCode.SEVER_CRITICAL_EXCEPTION,
            payload = null,
        ).also {
            logWarn("[Unhandled Exception] ${e::class.simpleName}: ${e.message}", e)
        }
}
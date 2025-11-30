package dev.lkdcode.api.advice

import dev.lkdcode.api.exception.ApiException
import dev.lkdcode.api.response.ApiResponse
import dev.lkdcode.api.response.ApiResponseCode
import dev.lkdcode.log.logInfo
import dev.lkdcode.log.logWarn
import io.r2dbc.spi.R2dbcDataIntegrityViolationException
import io.r2dbc.spi.R2dbcNonTransientResourceException
import io.r2dbc.spi.R2dbcRollbackException
import io.r2dbc.spi.R2dbcTransientResourceException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.resource.NoResourceFoundException
import org.springframework.web.server.MethodNotAllowedException
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono
import java.sql.SQLException


@RestControllerAdvice
class ApiAdvice {

    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException): Mono<ResponseEntity<ApiResponse<Any>>> =
        ApiResponse
            .clientError(e.getApiResponseCode(), e.payload)
            .doFinally {
                logInfo("ApiResponseCode: ${e.getApiResponseCode()}, Message: ${e.message()} Status: ${e.status()}")
            }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): Mono<ResponseEntity<ApiResponse<List<*>>>> {
        val response = e.bindingResult.fieldErrors.map {
            logInfo("Field: ${it.field}, Rejected value: ${it.rejectedValue}, Message: ${it.defaultMessage}")

            mapOf(
                "Field" to it.field,
                "Rejected value" to it.rejectedValue,
                "Message" to it.defaultMessage
            )
        }

        return ApiResponse.clientError(ApiResponseCode.REQUEST_INVALID, response)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): Mono<ResponseEntity<ApiResponse<List<*>>>> {
        val response = e.constraintViolations.map {
            logInfo("Path: ${it.rootBeanClass}, Property: ${it.propertyPath} Invalid value: ${it.invalidValue}, Message: ${it.message}")

            mapOf(
                "Invalid value" to it.propertyPath.toString(),
                "Message" to it.message
            )
        }

        return ApiResponse.clientError(ApiResponseCode.REQUEST_INVALID_DATA, response)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): Mono<ResponseEntity<ApiResponse<String>>> =
        ApiResponse
            .clientError(
                apiResponseCode = ApiResponseCode.REQUEST_INVALID_BODY,
                payload = "${e.message}",
            )
            .doFinally {
                logInfo("HttpMessageNotReadableException ${e.message}")
            }

    @ExceptionHandler(ServerWebInputException::class)
    fun handleServerWebInputException(e: ServerWebInputException): Mono<ResponseEntity<ApiResponse<String>>> =
        ApiResponse
            .clientError(
                apiResponseCode = ApiResponseCode.REQUEST_INVALID_BODY,
                payload = e.message,
            ).doFinally {
                logInfo("ServerWebInputException ${e.message}")
            }

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleWebExchangeBindException(
        e: WebExchangeBindException,
        request: ServerHttpRequest,
    ): Mono<ResponseEntity<ApiResponse<String>>> {
        val errorMessage = e.allErrors
            .joinToString("; ") { it.defaultMessage ?: "Invalid request" }

        return ApiResponse
            .clientError(
                apiResponseCode = ApiResponseCode.REQUEST_INVALID_BODY,
                payload = errorMessage
            )
            .doFinally {
                logInfo("BindException - URI: ${request.uri.path} | Errors: $errorMessage")
            }
    }

    @ExceptionHandler(MethodNotAllowedException::class)
    fun handleMethodNotAllowedException(
        e: MethodNotAllowedException,
        request: ServerHttpRequest
    ): Mono<ResponseEntity<ApiResponse<String>>> =
        ApiResponse
            .clientError(
                apiResponseCode = ApiResponseCode.REQUEST_UNSUPPORTED_METHOD,
                payload = "${e.httpMethod} ${request.uri.path} ${e.message}"
            )
            .doFinally {
                logInfo("MethodNotAllowed - Method: ${e.httpMethod} | URI: ${request.uri.path}")
            }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(e: NoResourceFoundException): Mono<ResponseEntity<ApiResponse<String>>> =
        ApiResponse
            .clientError(
                apiResponseCode = ApiResponseCode.REQUEST_UNSUPPORTED_REQUEST,
                payload = "API: /${e.message}, Message: ${e.message}",
            )
            .doFinally {
                logInfo("End-Point: ${e.message} Message: ${e.message}")
            }

    @ExceptionHandler(SQLException::class)
    fun handleSQLException(e: SQLException): Mono<ResponseEntity<ApiResponse<Void>>> =
        ApiResponse
            .serverError<Void>(ApiResponseCode.SEVER_SQL_EXCEPTION)
            .doFinally {
                logWarn("SQLException Message: ${e.message}")
            }

    @ExceptionHandler(R2dbcDataIntegrityViolationException::class)
    fun handleR2dbcDataIntegrityViolationException(e: R2dbcDataIntegrityViolationException): Mono<ResponseEntity<ApiResponse<Void>>> =
        ApiResponse
            .serverError<Void>(ApiResponseCode.SEVER_DATA_INTEGRITY_VIOLATION_EXCEPTION)
            .doFinally { logWarn("R2dbcDataIntegrityViolationException Message: ${e.message}") }

    @ExceptionHandler(R2dbcTransientResourceException::class)
    fun handleR2dbcTransientResourceException(e: R2dbcTransientResourceException): Mono<ResponseEntity<ApiResponse<Void>>> =
        ApiResponse
            .serverError<Void>(ApiResponseCode.SEVER_TRANSIENT_RESOURCE_EXCEPTION)
            .doFinally { logWarn("R2dbcTransientResourceException Message: ${e.message}") }

    @ExceptionHandler(R2dbcNonTransientResourceException::class)
    fun handleR2dbcNonTransientResourceException(e: R2dbcNonTransientResourceException): Mono<ResponseEntity<ApiResponse<Void>>> =
        ApiResponse
            .serverError<Void>(ApiResponseCode.SEVER_NON_TRANSIENT_RESOURCE_EXCEPTION)
            .doFinally { logWarn("R2dbcNonTransientResourceException Message: ${e.message}") }

    @ExceptionHandler(R2dbcRollbackException::class)
    fun handleR2dbcRollbackException(e: R2dbcRollbackException): Mono<ResponseEntity<ApiResponse<Void>>> =
        ApiResponse
            .serverError<Void>(ApiResponseCode.SEVER_ROLL_BACK_EXCEPTION)
            .doFinally { logWarn("R2dbcRollbackException Message: ${e.message}") }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): Mono<ResponseEntity<ApiResponse<Void>>> =
        ApiResponse
            .serverError<Void>(ApiResponseCode.SEVER_UNHANDLED_EXCEPTION)
            .doFinally {
                logWarn("[Runtime Exception] ${e::class.simpleName}: ${e.message}", e)
            }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): Mono<ResponseEntity<ApiResponse<Void>>> =
        ApiResponse
            .serverError<Void>(ApiResponseCode.SEVER_CRITICAL_EXCEPTION)
            .doFinally { logWarn("[Unhandled Exception] ${e::class.simpleName}: ${e.message}", e) }

}

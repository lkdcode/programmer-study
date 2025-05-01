package lkdcode.wanted.ecommerce.framework.common.api.advice;


import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolationException;
import lkdcode.wanted.ecommerce.framework.common.api.response.ClientErrorResponse;
import lkdcode.wanted.ecommerce.framework.common.api.response.ServerErrorResponse;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleBaseException(final ApplicationException e) {
        return ResponseEntity.ok("hi");
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        e.getBindingResult().getFieldErrors().forEach(error ->
            log.info("[   MethodArgumentNotValidException   ] field: {}, rejected value: {}, message: {}",
                error.getField(), error.getRejectedValue(), error.getDefaultMessage()));

        return null;
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleConstraintViolationException(final ConstraintViolationException e) {
        e.getConstraintViolations().forEach(violation ->
            log.info("[   ConstraintViolationException    ] Property: {}, Invalid value: {}, Message: {}",
                violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage()));

        return null;
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.info("[   HttpMessageNotReadableException   ] cause: {}, message: {}",
            e.getCause() != null ? e.getCause().getMessage() : "N/A", e.getMessage());

        return null;
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleJsonMappingException(final JsonMappingException e) {
        log.info("[   JsonMappingException   ] Path: {}, Message: {}",
            e.getPathReference(), e.getOriginalMessage());

        return null;
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        log.info("[   MissingRequestHeaderException   ] missing header: {}, message: {}",
            e.getHeaderName(), e.getMessage());

        return null;
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ServerErrorResponse<?>> handleSQLException(final SQLException e) {
        log.warn("[   SQLException   ] SQLState: {}, ErrorCode: {}, message: {}",
            e.getSQLState(), e.getErrorCode(), e.getMessage());

        return null;
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ServerErrorResponse<?>> handleDataAccessException(final DataAccessException e) {
        log.warn("[   DataAccessException   ] message: {}", e.getMessage(), e);

        return null;
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerErrorResponse<?>> handleException(final Exception e) {
        log.error("[   Exception   ] localizedMessage: {}, message: {}", e.getLocalizedMessage(), e.getMessage(), e);

        return null;
    }
}
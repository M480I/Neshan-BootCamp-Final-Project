package com.etaxi.core.exception.handlers;

import com.etaxi.core.exception.ApiError;
import com.etaxi.core.exception.EntityNotFoundException;
import com.etaxi.core.exception.InvalidUsernameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String, String> errors = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(), // Key: field name
                        error -> error.getDefaultMessage(), // Value: error message
                        (existing, replacement) -> existing // In case of duplicate keys, keep the existing value
                ));
        ApiError<Map<String, String>> apiError = new ApiError<>(
                errors,
                httpStatus
        );

        return ResponseEntity.status(httpStatus).body(apiError);

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError<String>> handleUsernameNotFound(
            UsernameNotFoundException exception
    ) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiError<String> apiError = new ApiError<>(
                exception.getMessage(),
                httpStatus
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError<String>> handleBadCredential(
            BadCredentialsException exception
    ) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiError<String> apiError = new ApiError<>(
                exception.getMessage(),
                httpStatus
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            EntityNotFoundException.class,
            InvalidUsernameException.class
    })
    public ResponseEntity<ApiError<String>> handleRuntimeExtendedExceptions(
            RuntimeException exception
    ) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiError<String> apiError = new ApiError<>(
                exception.getMessage(),
                httpStatus
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}

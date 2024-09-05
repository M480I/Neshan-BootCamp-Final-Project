package com.etaxi.core.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError<T> {

    T error;
    Integer statusCode;
    String status;

    public ApiError(T error, HttpStatus status) {
        this.error = error;
        this.statusCode = status.value();
        this.status = status.toString();
    }

}

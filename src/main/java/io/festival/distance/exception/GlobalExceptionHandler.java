package io.festival.distance.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DistanceException.class)
    protected ResponseEntity<ErrorResponseEntity> errorCodeResponseEntity(DistanceException ex){
        return ErrorResponseEntity.responseEntity(ex.getErrorCode());
    }
}

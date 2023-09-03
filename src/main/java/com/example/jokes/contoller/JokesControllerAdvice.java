package com.example.jokes.contoller;

import com.example.jokes.exception.NoSuchCategoryException;
import com.example.jokes.exception.RateLimiterException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class JokesControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ NoSuchCategoryException.class })
    public ResponseEntity<?> handleNoSuchCategoryException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ExceptionDto(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ RateLimiterException.class })
    public ResponseEntity<?> handleRateLimiterException (
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ExceptionDto(ex.getMessage()), new HttpHeaders(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<?> handleGeneralException (
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ExceptionDto("The service is currently busy"), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Getter
    @NoArgsConstructor
    private static class ExceptionDto {
        private String message;

        public ExceptionDto(String message) {
            this.message = message;
        }
    }
}

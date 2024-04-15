package org.video.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.video.exception.ApiException;
import org.video.exception.DataNotFound;
import org.video.exception.DatabasePersistenceException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<Object> handleAPIException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({DataNotFound.class})
    public ResponseEntity<Object> handleDataNotFoundException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({DatabasePersistenceException.class})
    public ResponseEntity<Object> handleDatabaseException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}

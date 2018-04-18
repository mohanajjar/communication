package fr.communication.controller;

import fr.communication.utils.NoSuchResourceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchResourceException.class)
    public ResponseEntity<Object> handleNoSuchResourceException(final NoSuchResourceException ex, WebRequest request) {
        String bodyOfResponse = "No account matched provided Id";
        return ResponseEntity.badRequest().body(bodyOfResponse+ex.getMessage());
    }
}